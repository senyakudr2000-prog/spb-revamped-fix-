package dev.veilfix.mixin;

import foundry.veil.api.client.imgui.CodeEditor;
import java.lang.reflect.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import sun.misc.Unsafe;

@Mixin(targets = "foundry.veil.api.resource.editor.TextFileEditor", remap = false)
public abstract class TextFileEditorMixin {
    private static final Logger VEILFIX_LOGGER = LoggerFactory.getLogger("veilfix");

    @Redirect(
            method = "<init>",
            at = @At(value = "NEW", target = "Lfoundry/veil/api/client/imgui/CodeEditor;", remap = false),
            remap = false
    )
    private CodeEditor veilfix$safeCodeEditor(String saveText) {
        try {
            return new CodeEditor(saveText);
        } catch (Throwable t) {
            VEILFIX_LOGGER.warn("ImGui natives unavailable, Veil code editor disabled: {}", t.toString());
            try {
                Field f = Unsafe.class.getDeclaredField("theUnsafe");
                f.setAccessible(true);
                return (CodeEditor) ((Unsafe) f.get(null)).allocateInstance(CodeEditor.class);
            } catch (Throwable t2) {
                throw new RuntimeException("veilfix: cannot create dummy CodeEditor", t2);
            }
        }
    }
}
