package dev.arrokoth.annoying_popup;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod(AnnoyingPopup.MOD_ID)
public class AnnoyingPopup {
    public static final String MOD_ID = "annoying_popup";
//    private static final Logger LOGGER = LogUtils.getLogger();

    public AnnoyingPopup() {
        MinecraftForge.EVENT_BUS.register(this);
    }
}
