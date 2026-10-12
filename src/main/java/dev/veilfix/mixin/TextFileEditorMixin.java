package dev.veilfix.mixin;

import foundry.veil.api.client.imgui.CodeEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * TextFileEditor создаёт CodeEditor (-> ImGui TextEditor) прямо при старте Veil, даже когда
 * ImGui выключен. Если нативки ImGui нет, не падаем, а оставляем редактор пустым.
 */
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
            return null;
        }
    }
}
