package org.fentanylsolutions.cmotd.handler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.ServerStatusResponse;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;

import org.apache.commons.lang3.text.WordUtils;
import org.fentanylsolutions.cmotd.Config;
import org.fentanylsolutions.cmotd.ModCache;
import org.fentanylsolutions.cmotd.util.ProxiedUtils;
import org.fentanylsolutions.cmotd.util.TextUtil;
import org.fentanylsolutions.cmotd.util.Util;

import com.mojang.authlib.GameProfile;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EventsHandler {

    private float tickCounter = -69;
    private float tickCounterFile = -69;

    @SuppressWarnings("unused")
    @SideOnly(Side.SERVER)
    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent e) {
        if (e.phase == TickEvent.Phase.START) {
            this.tickCounter++;
            this.tickCounterFile++;
        }

        if (this.tickCounterFile < 0 || this.tickCounterFile / 20.0 >= Config.reload_interval) {
            this.tickCounterFile = 0;
            ModCache.cacheMOTDListFile();
            ModCache.cachePlayerListFile();
        }

        if (this.tickCounter < 0 || this.tickCounter / 20.0 >= Config.server_stat_reload_interval) {
            this.tickCounter = 0;
        } else if (tickCounterFile != 0) {
            return;
        }

        MinecraftServer server = ProxiedUtils.getServer();
        List<String> motdList = ModCache.getMOTDListFileCache();
        int index = (new Random()).nextInt(motdList.size());
        String selectedMOTD = motdList.get(index);
        selectedMOTD = randomFromString(selectedMOTD);
        selectedMOTD = selectedMOTD.replace("\\|", "%%PIPE%%");
        selectedMOTD = selectedMOTD.replace("|", "\n");
        selectedMOTD = selectedMOTD.replace("%%PIPE%%", "|");
        selectedMOTD = selectedMOTD.replace("{playercount}", Integer.toString(server.getCurrentPlayerCount()));
        selectedMOTD = selectedMOTD.replace("{maxplayers}", Integer.toString(server.getMaxPlayers()));
        selectedMOTD = selectedMOTD.replace(
            "{difficulty}",
            WordUtils.capitalizeFully(
                server.func_147135_j()
                    .name()));
        selectedMOTD = selectedMOTD.replace("{radio}", TextUtil.RADIO_BARS);
        selectedMOTD = selectedMOTD.replace("{mcversion}", server.getMinecraftVersion());
        selectedMOTD = selectedMOTD.replace("\\&", "%%AND%%");
        selectedMOTD = selectedMOTD.replace("&", "§");
        selectedMOTD = selectedMOTD.replace("%%AND%%", "&");
        server.func_147134_at()
            .func_151315_a(new ChatComponentText(selectedMOTD));

        server.func_147134_at()
            .func_151319_a(
                new ServerStatusResponse.PlayerCountData(server.getMaxPlayers(), server.getCurrentPlayerCount()));

        List<GameProfile> profileList = new ArrayList<>();
        for (String entry : ModCache.getPlayerListFileCache()) {
            if (entry.startsWith("#")) {
                continue;
            }
            if (entry.startsWith("\\#")) {
                entry = entry.substring(1);
            }
            entry = randomFromString(entry);
            entry = entry.replace("{playercount}", Integer.toString(server.getCurrentPlayerCount()));
            entry = entry.replace("{maxplayers}", Integer.toString(server.getMaxPlayers()));
            entry = entry.replace(
                "{difficulty}",
                WordUtils.capitalizeFully(
                    server.func_147135_j()
                        .name()));
            entry = entry.replace("{radio}", TextUtil.RADIO_BARS);
            entry = entry.replace("{mcversion}", server.getMinecraftVersion());
            entry = entry.replace("&&", "%%AND%%");
            entry = entry.replace("&", "§");
            entry = entry.replace("%%AND%%", "&");
            // profileList.add(new GameProfile(CustomMOTD.fakePlayerUUID, entry));
            if (entry.trim()
                .equalsIgnoreCase("{playerlist}")) {
                Collection<EntityPlayerMP> realPlayers = server.getConfigurationManager().playerEntityList;
                if (!realPlayers.isEmpty()) {
                    for (EntityPlayerMP player : realPlayers) {
                        profileList.add(player.getGameProfile());
                    }
                } else {
                    if (!Config.no_players_msg.isEmpty()) {
                        profileList.add(new GameProfile(Util.fakePlayerUUID, Config.no_players_msg));
                    }
                }
            } else {
                String res = getPlayerListString(false);
                if (!res.isEmpty()) {
                    entry = entry.replace("{playerlist}", res);
                }
                profileList.add(new GameProfile(Util.fakePlayerUUID, entry));
            }
        }
        GameProfile[] customPlayerList = profileList.toArray(new GameProfile[0]);
        server.func_147134_at()
            .func_151318_b()
            .func_151330_a(customPlayerList);
    }

    @Deprecated
    private String getPlayerListString() {
        MinecraftServer server = ProxiedUtils.getServer();
        if (server.getAllUsernames().length == 0) {
            return Config.no_players_msg;
        }
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < Math.min(server.getAllUsernames().length, 10); i++) {
            res.append(server.getAllUsernames()[i]);
            if (i < server.getAllUsernames().length - 1) {
                res.append(", ");
            }
        }
        return res.toString();
    }

    private String getPlayerListString(boolean oneLine) {
        MinecraftServer server = ProxiedUtils.getServer();
        if (server.getAllUsernames().length == 0) {
            return Config.no_players_msg;
        }
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < Math.min(server.getAllUsernames().length, 10); i++) {
            res.append(server.getAllUsernames()[i]);
            if (i < server.getAllUsernames().length - 1) {
                if (oneLine) {
                    res.append(", ");
                } else {
                    res.append(System.lineSeparator());
                }
            }
        }
        return res.toString();
    }

    private String randomFromString(String list) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < list.length(); i++) {
            if (i < list.length() - 7 && list.startsWith("{random", i)) {
                List<String> randomWords = new ArrayList<>();
                String temp = "";
                int counter = i + 8;
                int openBrackets = 1;
                while (!(String.valueOf(list.charAt(counter))
                    .equals("}") && openBrackets == 1)) {
                    if (list.charAt(counter) == ',') {
                        randomWords.add(temp);
                        temp = "";
                    } else if (list.charAt(counter + 1) == '}' && openBrackets == 1) {
                        temp += list.charAt(counter);
                        randomWords.add(temp);
                    } else {
                        temp += list.charAt(counter);
                        if (list.charAt(counter) == '{') {
                            openBrackets++;
                        } else if (list.charAt(counter) == '}') {
                            openBrackets--;
                        }
                    }
                    counter++;
                }
                for (int j = 0; j < randomWords.size(); j++) {
                    if (Character.isSpaceChar(
                        randomWords.get(j)
                            .charAt(0))) {
                        randomWords.set(
                            j,
                            randomWords.get(j)
                                .substring(1));
                    }
                    if (!randomWords.get(j)
                        .isEmpty() && Character.isSpaceChar(
                            randomWords.get(j)
                                .charAt(
                                    randomWords.get(j)
                                        .length() - 1))) {
                        randomWords.set(
                            j,
                            randomWords.get(j)
                                .substring(
                                    0,
                                    randomWords.get(j)
                                        .length() - 1));
                    }
                }
                Random rand = new Random();
                res.append(randomWords.get(rand.nextInt(randomWords.size())));
                // i += counter - 8;
                i = counter;
            } else {
                res.append(list.charAt(i));
            }
        }
        return res.toString();
    }
}
