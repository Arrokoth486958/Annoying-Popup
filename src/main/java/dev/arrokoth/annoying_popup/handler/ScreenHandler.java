package dev.arrokoth.AnnoyingPopup.handler;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.arrokoth.annoying_popup.AnnoyingPopup;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

/**
 * @author Arrokoth
 * @project AnnoyingPopup
 * @copyright Copyright © 2023 Arrokoth All Rights Reserved.
 */

@Mod.EventBusSubscriber(modid = AnnoyingPopup.MOD_ID, value = Dist.CLIENT)
public class ScreenHandler {
    public static final String COUNTRY = Locale.getDefault().getCountry();
    public static final int TEXT_COLOR = new Color(127, 127, 127).getRGB();
    public static final int HYPERLINK_COLOR = new Color(63, 127, 255).getRGB();
    private static boolean isOpening = false;

    @SubscribeEvent
    public static void init(final ScreenEvent.Init event) {
        if (event.getScreen() instanceof TitleScreen && Objects.equals(COUNTRY, "CN")){
            isOpening = true;
        }
    }

    @SubscribeEvent
    public static void render(final ScreenEvent.Render event) {
        if (event.getScreen() instanceof TitleScreen && isOpening) {
            RenderSystem.assertOnRenderThread();

            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.enableDepthTest();
            RenderSystem.depthFunc(519);

            event.getGuiGraphics().blit(new ResourceLocation("minecraft", "textures/block/dirt.png"), 0, 0, 0, 0, Minecraft.getInstance().getWindow().getGuiScaledWidth(), Minecraft.getInstance().getWindow().getGuiScaledHeight(), 32, 32);
            event.getGuiGraphics().blit(new ResourceLocation("annoying_popup", "textures/gui/blur.png"), 0, 0, 0, 0, Minecraft.getInstance().getWindow().getGuiScaledWidth(), Minecraft.getInstance().getWindow().getGuiScaledHeight(), 1, 1);
            event.getGuiGraphics().blit(new ResourceLocation("annoying_popup", "textures/gui/background.png"), Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 - 174 / 2, Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2 - 232 / 2, 0, 0, 174, 232, 174, 232);
            event.getGuiGraphics().blit(new ResourceLocation("annoying_popup", "textures/gui/minecraft.png"), Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 - 174 / 2, Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2 - 232 / 2 - 8, 0, 0, 174, 87, 174, 87);

            Component text = Component.translatable("popup.text");
            String[] split = text.getString().split("\n");
            for (int i = 0; i < split.length; i++) {
                String part = split[i];
                event.getGuiGraphics().drawCenteredString(Minecraft.getInstance().font, Component.literal(part), Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2, Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2 - 232 / 2 + 72 + 12 * i, TEXT_COLOR);
            }

            if (event.getMouseX() >= Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 - 128 / 2 && event.getMouseX() <= Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 + 128 * 2 &&
                event.getMouseY() >= Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2 + 232 / 2 - 76 && event.getMouseY() <= Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2 + 232 / 2 - 76 + 32) {
                event.getGuiGraphics().blit(new ResourceLocation("annoying_popup", "textures/gui/button_hover.png"), Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 - 128 / 2, Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2 + 232 / 2 - 76, 0, 0, 128, 32, 128, 32);
            } else {
                event.getGuiGraphics().blit(new ResourceLocation("annoying_popup", "textures/gui/button.png"), Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 - 128 / 2, Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2 + 232 / 2 - 76, 0, 0, 128, 32, 128, 32);
            }

            event.getGuiGraphics().drawCenteredString(Minecraft.getInstance().font, Component.translatable("popup.info"), Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2, Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2 + 232 / 2 - 32, TEXT_COLOR);

            if (event.getMouseX() >= Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 - 72 / 2 && event.getMouseX() <= Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 + 72 / 2 &&
                    event.getMouseY() >= Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2 + 232 / 2 - 20 && event.getMouseY() <= Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2 + 232 / 2 - 20 + 8) {
                event.getGuiGraphics().drawCenteredString(Minecraft.getInstance().font, Component.translatable("popup.hyperlink").setStyle(Style.EMPTY.withUnderlined(true)), Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2, Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2 + 232 / 2 - 20, HYPERLINK_COLOR);
            } else {
                event.getGuiGraphics().drawCenteredString(Minecraft.getInstance().font, Component.translatable("popup.hyperlink"), Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2, Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2 + 232 / 2 - 20, TEXT_COLOR);
            }
        }
    }

    @SubscribeEvent
    public static void mouseButtonPressed(final ScreenEvent.MouseButtonPressed event) {
        if (isOpening && event.isCancelable()) {
            if (event.getMouseX() >= (double) (Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 - 128 / 2) && event.getMouseX() <= (double) (Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 + 128 * 2) &&
                    event.getMouseY() >= (double) (Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2 + 232 / 2 - 76) && event.getMouseY() <= (double) (Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2 + 232 / 2 - 76 + 32)) {
                System.out.println(114514);
                try {
                    if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                        Runtime.getRuntime().exec(new String[] {"explorer", "https://mc.163.com/"});
                    } else {
                        Runtime.getRuntime().exec(new String[] {"open", "https://mc.163.com/"});
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
//                if (Desktop.isDesktopSupported()) {
//                    try {
//                        Desktop.getDesktop().browse(new URI("https://mc.163.com/"));
//                    } catch (Exception e) {
//                        throw new RuntimeException(e);
//                    }
//                }
            }
            if (event.getMouseX() >= (double) (Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 - 72 / 2) && event.getMouseX() <= (double) (Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 + 72 / 2) &&
                    event.getMouseY() >= (double) (Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2 + 232 / 2 - 20) && event.getMouseY() <= (double) (Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2 + 232 / 2 - 20 + 8)) {
                isOpening = false;
            }
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void mouseButtonReleased(final ScreenEvent.MouseButtonReleased event) {
        if (isOpening && event.isCancelable()) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void keyPressed(final ScreenEvent.KeyPressed event) {
        if (isOpening && event.isCancelable()) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void keyReleased(final ScreenEvent.KeyReleased event) {
        if (isOpening && event.isCancelable()) {
            event.setCanceled(true);
        }
    }
}
