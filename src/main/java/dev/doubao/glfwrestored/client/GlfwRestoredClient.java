package dev.doubao.glfwrestored.client;

import dev.doubao.glfwrestored.GlfwRestored;
import net.fabricmc.api.ClientModInitializer;
import org.lwjgl.glfw.GLFW;

/**
 * Client entrypoint.
 *
 * <p>Vanilla 26.3 no longer calls {@link GLFW#glfwInit()} — the window and input
 * stack is SDL3. We re-initialise GLFW here so that (a) the native library is
 * loaded for any mod code that still touches GLFW, and (b) the {@code WindowMixin}
 * has a live GLFW context to bridge into.</p>
 */
public class GlfwRestoredClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        GlfwRestored.LOGGER.info("[GLFW Restored] client initialising — re-arming GLFW on top of the SDL3 backend");

        if (!GLFW.glfwInit()) {
            long err = GLFW.glfwGetError(null);
            GlfwRestored.LOGGER.error("[GLFW Restored] glfwInit() FAILED (error code {}). "
                    + "The mod will load but GLFW bridge calls will be no-ops.", err);
            return;
        }

        // Hint: we do *not* create our own window here. Vanilla's SDL window is
        // still the visible game window. The long-term goal of the mod is to
        // swap the native handle that Window talks to; until that mixin is
        // complete, this just proves GLFW is callable from the runtime.
        GlfwRestored.LOGGER.info("[GLFW Restored] glfwInit() OK — GLFW {} is live alongside the SDL3 game window",
                GLFW.glfwGetVersionString());
    }
}
