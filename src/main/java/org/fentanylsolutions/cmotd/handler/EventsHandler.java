package org.fentanylsolutions.cmotd.handler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.ServerStatusResponse;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;

import org.fentanylsolutions.cmotd.Config;
import org.fentanylsolutions.cmotd.CustomMOTD;
import org.fentanylsolutions.cmotd.ModCache;
import org.fentanylsolutions.cmotd.ModCompat;
import org.fentanylsolutions.cmotd.handler.replacers.LycaniteReplacer;
import org.fentanylsolutions.cmotd.handler.replacers.MiscReplace;
import org.fentanylsolutions.cmotd.handler.replacers.RandomThingsReplace;
import org.fentanylsolutions.cmotd.handler.replacers.SereneSeasonsReplace;
import org.fentanylsolutions.cmotd.handler.replacers.SpecialReplace;
import org.fentanylsolutions.cmotd.handler.replacers.TFCReplacer;
import org.fentanylsolutions.cmotd.handler.replacers.VanillaReplace;
import org.fentanylsolutions.cmotd.util.ProxiedUtils;
import org.fentanylsolutions.cmotd.util.Util;

import com.mojang.authlib.GameProfile;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EventsHandler {

    private float tickCounter = -69;
    private float tickCounterFile = -69;

    GameProfile[] customPlayerList;
    MinecraftServer server;

    @SuppressWarnings("unused")
    @SideOnly(Side.SERVER)
    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent e) {
        if (e.phase == TickEvent.Phase.START) {
            if (server == null) {
                server = ProxiedUtils.getServer();
            }
            server.func_147134_at()
                .func_151319_a(
                    new ServerStatusResponse.PlayerCountData(server.getMaxPlayers(), server.getCurrentPlayerCount()));
            server.func_147134_at()
                .func_151318_b()
                .func_151330_a(customPlayerList);

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

        List<String> motdList = ModCache.getMOTDListFileCache();
        int index = (new Random()).nextInt(motdList.size());
        String selectedMOTD = motdList.get(index);

        selectedMOTD = VanillaReplace.replaceVars(selectedMOTD);
        selectedMOTD = MiscReplace.replaceVars(selectedMOTD);
        if (ModCompat.RTLoaded) {
            selectedMOTD = RandomThingsReplace.replaceVars(selectedMOTD);
        }
        if (ModCompat.SereneSeasonsLoaded) {
            selectedMOTD = SereneSeasonsReplace.replaceVars(selectedMOTD);
        }
        if (ModCompat.LycanitesLoaded) {
            selectedMOTD = LycaniteReplacer.replaceVars(selectedMOTD);
        }
        if (ModCompat.TFCLoaded) {
            selectedMOTD = TFCReplacer.replaceVars(selectedMOTD);
        }

        /* Must be last */
        selectedMOTD = SpecialReplace.replaceVars(selectedMOTD);
        selectedMOTD = selectedMOTD.replace("%%REMOVEME%%", "");

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

            entry = VanillaReplace.replaceVars(entry);
            entry = MiscReplace.replaceVars(entry);
            if (ModCompat.RTLoaded) {
                entry = RandomThingsReplace.replaceVars(entry);
            }
            if (ModCompat.SereneSeasonsLoaded) {
                entry = SereneSeasonsReplace.replaceVars(entry);
            }
            if (ModCompat.LycanitesLoaded) {
                entry = LycaniteReplacer.replaceVars(entry);
            }
            if (ModCompat.TFCLoaded) {
                entry = TFCReplacer.replaceVars(entry);
            }

            /* Must be last */
            entry = SpecialReplace.replaceVars(entry);

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
                if (!entry.trim()
                    .equals("%%REMOVEME%%")) {
                    entry = entry.replace("%%REMOVEME%%", "");
                    profileList.add(new GameProfile(Util.fakePlayerUUID, entry));
                }
            }
        }
        customPlayerList = profileList.toArray(new GameProfile[0]);
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

    @SuppressWarnings("unused")
    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent e) {
        CustomMOTD.LOG.debug("Player " + e.player.getDisplayName() + " joined");
        tickCounter = -69;
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public void onPlayerLEave(PlayerEvent.PlayerLoggedOutEvent e) {
        CustomMOTD.LOG.debug("Player " + e.player.getDisplayName() + " left");
        tickCounter = -69;
    }
}
