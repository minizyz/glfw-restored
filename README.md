# GLFW Restored

A Fabric mod for **Minecraft Java Edition 26.3** ("Wilderness Bound", 2026-09-15) that puts the runtime window back on **GLFW** after Mojang swapped the windowing/input backend to **SDL3**.

## Why this exists

Starting in 26.3, Minecraft no longer uses GLFW for window management, keybind handling, or input polling. The engine now talks to SDL3 directly:

- `com.mojang.blaze3d.platform.Window` is SDL3-backed.
- Key codes are SDL scancodes (`InputConstants.KEY_ESCAPE == 41`), not GLFW key codes.
- The LWJGL GLFW natives are no longer shipped on the classpath.
- Text input goes through `TextInputManager` + SDL's IME, not GLFW's.

This mod's goal is to make the runtime window live on GLFW again — for modders who depend on GLFW window handles, key codes, or event polling, and for anyone who wants the pre-26.3 input behaviour back.

## Status

**v0.1.0 — proof of concept.**

What works:

- Re-declares the LWJGL GLFW dependency so `org.lwjgl.glfw.GLFW` is on the mod classpath.
- Calls `glfwInit()` on the client entrypoint, so GLFW natives are loaded and callable.
- Mixins into `com.mojang.blaze3d.platform.Window` after construction and logs the current GLFW context handle.

What is not done yet (the hard part):

- Attaching GLFW to the OS-level window that SDL3 created (or replacing the SDL window with a GLFW one).
- Routing input events from `glfwPollEvents()` into Minecraft's input pipeline instead of SDL events.
- Re-mapping SDL scancodes back to GLFW key codes for legacy mods.
- Context sharing between the SDL GL context and a GLFW-created one.

Treat this as the starting point, not a finished drop-in.

## Toolchain

| Component | Version |
|---|---|
| Minecraft | 26.3 |
| Fabric Loader | 0.19.5 |
| Fabric API | 0.161.0+26.3 |
| Fabric Loom | 1.17 |
| Gradle | 9.6.0 |
| Java | 25 |
| LWJGL GLFW | 3.4.3 |

MC 26.1+ is unobfuscated, so no Yarn mappings / remap step.

## Build

```bash
./gradlew build
# jar lands in build/libs/glfw-restored-0.1.0.jar
```

Drop the jar into your `mods/` folder alongside Fabric API.

## License

MIT
