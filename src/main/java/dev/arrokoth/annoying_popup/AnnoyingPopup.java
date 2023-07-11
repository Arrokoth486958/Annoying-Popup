package dev.arrokoth.annoying_popup;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

import java.util.Arrays;

@Mod(AnnoyingPopup.MOD_ID)
public class AnnoyingPopup {
    public static final String MOD_ID = "annoying_popup";
    public static final boolean IS_NETEASE = isNetEaseMinecraft();
//    private static final Logger LOGGER = LogUtils.getLogger();

    public AnnoyingPopup() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static boolean isNetEaseMinecraft() {
        for (int i = 0; i < Package.getPackages().length; i++) {
            String name = Package.getPackages()[i].getName();
            if (name.contains("netease")) {
                return true;
            }
        }
        return
                ModList.get().isLoaded("networkmod") ||
                        ModList.get().isLoaded("friendplaymod") ||
                        ModList.get().isLoaded("antimod") ||
                        ModList.get().isLoaded("updatecore") ||
                        ModList.get().isLoaded("playermanagermod") ||
                        ModList.get().isLoaded("fullscreenpopup");
    }
}
