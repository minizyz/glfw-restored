package dev.doubao.glfwrestored.client.mixin;

import com.mojang.blaze3d.platform.Window;
import dev.doubao.glfwrestored.GlfwRestored;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Hooks the vanilla {@link Window} after its SDL3-backed construction.
 *
 * <p>This is the conceptual heart of the mod. In 26.3, {@code Window} owns an
 * SDL3 window/context; GLFW is gone. Long term, this mixin is where we would:
 * <ul>
 *   <li>read the native window handle out of the SDL side,</li>
 *   <li>wrap it with {@link GLFW#glfwWindowHint} / attach a GLFW context to the
 *       same OS-level window, and</li>
 *   <li>redirect input polling away from SDL events toward {@code glfwPollEvents()}.</li>
 * </ul>
 *
 * <p>For v0.1.0 this injection only logs the window handle and confirms GLFW
 * is callable — a compile-and-load proof of concept against the 26.3 toolchain.
 * The exact target method signature will be re-pointed once the decompiled
 * 26.3 sources are available locally (the constructor descriptor changed
 * between the GLFW and SDL eras).</p>
 */
@Mixin(Window.class)
public abstract class WindowMixin {

    @Inject(method = "<init>", at = @At("TAIL"))
    private void glfwrestored$afterWindowInit(CallbackInfo ci) {
        long handle = GLFW.glfwGetCurrentContext();
        GlfwRestored.LOGGER.info("[GLFW Restored] Window constructed (SDL3 backend). "
                + "Current GLFW context handle = {} — bridge hook armed.",
                handle == 0L ? "none (GLFW window not yet attached)" : "0x" + Long.toHexString(handle));
    }
}
