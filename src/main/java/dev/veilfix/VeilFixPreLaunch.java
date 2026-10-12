package dev.veilfix;

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

/**
 * На ARM (телефоны) у Veil нет нативной библиотеки ImGui, поэтому выключаем ImGui штатным
 * флагом Veil до того, как он загрузится. Это отключает только отладочный UI (шейдер-редактор).
 */
public class VeilFixPreLaunch implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() {
        String arch = System.getProperty("os.arch", "").toLowerCase();
        boolean arm = arch.contains("aarch64") || arch.contains("arm");
        if (arm && System.getProperty("veil.disableImgui") == null) {
            System.setProperty("veil.disableImgui", "true");
        }
    }
}
