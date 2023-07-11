package dev.arrokoth.annoying_popup.handler;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.arrokoth.annoying_popup.AnnoyingPopup;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
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
    public static final int TEXT_COLOR = new Color(0, 0, 0).getRGB();
    public static final int HYPERLINK_COLOR = new Color(63, 63, 255).getRGB();
    private static boolean isOpening = false;

    public static boolean shouldOpen(Screen screen) {
        if (AnnoyingPopup.IS_NETEASE) {
            return false;
        }

        return screen instanceof TitleScreen ||
                screen instanceof JoinMultiplayerScreen;
    }

    @SubscribeEvent
    public static void init(final ScreenEvent.Init event) {
        if (shouldOpen(event.getScreen()) && Objects.equals(COUNTRY, "CN")){
            isOpening = true;
        }
    }

    private static void drawCenteredString(GuiGraphics graphics, Font p_282901_, Component p_282456_, int p_283083_, int p_282276_, int p_281457_) {
        FormattedCharSequence formattedcharsequence = p_282456_.getVisualOrderText();
        graphics.drawString(p_282901_, formattedcharsequence, p_283083_ - p_282901_.width(formattedcharsequence) / 2, p_282276_, p_281457_, false);
    }

    @SubscribeEvent
    public static void render(final ScreenEvent.Render event) {
        if (shouldOpen(event.getScreen()) && isOpening) {
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
                drawCenteredString(event.getGuiGraphics(), Minecraft.getInstance().font, Component.literal(part), Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2, Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2 - 232 / 2 + 72 + 12 * i, TEXT_COLOR);
            }

            if (event.getMouseX() >= Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 - 128 / 2 && event.getMouseX() <= Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 + 128 / 2 &&
                event.getMouseY() >= Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2 + 232 / 2 - 76 && event.getMouseY() <= Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2 + 232 / 2 - 76 + 32) {
                event.getGuiGraphics().blit(new ResourceLocation("annoying_popup", "textures/gui/button_hover.png"), Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 - 128 / 2, Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2 + 232 / 2 - 76, 0, 0, 128, 32, 128, 32);
            } else {
                event.getGuiGraphics().blit(new ResourceLocation("annoying_popup", "textures/gui/button.png"), Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 - 128 / 2, Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2 + 232 / 2 - 76, 0, 0, 128, 32, 128, 32);
            }

            drawCenteredString(event.getGuiGraphics(), Minecraft.getInstance().font, Component.translatable("popup.info"), Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2, Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2 + 232 / 2 - 32, TEXT_COLOR);

            if (event.getMouseX() >= Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 - 72 / 2 && event.getMouseX() <= Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 + 72 / 2 &&
                    event.getMouseY() >= Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2 + 232 / 2 - 20 && event.getMouseY() <= Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2 + 232 / 2 - 20 + 8) {
                drawCenteredString(event.getGuiGraphics(), Minecraft.getInstance().font, Component.translatable("popup.hyperlink").setStyle(Style.EMPTY.withUnderlined(true)), Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2, Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2 + 232 / 2 - 20, HYPERLINK_COLOR);
            } else {
                drawCenteredString(event.getGuiGraphics(), Minecraft.getInstance().font, Component.translatable("popup.hyperlink"), Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2, Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2 + 232 / 2 - 20, TEXT_COLOR);
            }
        }
    }

    @SubscribeEvent
    public static void mouseButtonPressed(final ScreenEvent.MouseButtonPressed event) {
        if (isOpening && event.isCancelable()) {
            if (event.getMouseX() >= (double) (Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 - 128 / 2) && event.getMouseX() <= (double) (Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 + 128 / 2) &&
                    event.getMouseY() >= (double) (Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2 + 232 / 2 - 76) && event.getMouseY() <= (double) (Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2 + 232 / 2 - 76 + 32)) {
                try {
                    Util.getPlatform().openUri(new URI("https://mc.163.com/"));
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
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
        if (shouldOpen(event.getScreen()) && isOpening && event.isCancelable()) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void keyPressed(final ScreenEvent.KeyPressed event) {
        if (shouldOpen(event.getScreen()) && isOpening && event.isCancelable()) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void keyReleased(final ScreenEvent.KeyReleased event) {
        if (shouldOpen(event.getScreen()) && isOpening && event.isCancelable()) {
            event.setCanceled(true);
        }
    }
}
