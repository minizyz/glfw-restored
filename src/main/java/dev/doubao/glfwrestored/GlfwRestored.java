package dev.doubao.glfwrestored;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Common entrypoint for GLFW Restored.
 *
 * <p>MC 26.3 ("Wilderness Bound", 2026-09-15) replaced the GLFW windowing/input
 * backend with SDL3 at the engine level. This mod's job is to bring GLFW back
 * to the runtime. The client entrypoint does the actual window work; this class
 * only holds shared constants and the logger.</p>
 */
public final class GlfwRestored {
    public static final String MOD_ID = "glfw-restored";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private GlfwRestored() {}
}
