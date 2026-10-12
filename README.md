# VeilFix (ARM / Android)

Мод для Fabric 1.20.1, который позволяет запускать Veil на телефоне (Zalith/Pojav, aarch64).

Что делает:
1. На ARM ставит `veil.disableImgui=true` до загрузки Veil (штатный флаг Veil).
2. Mixin в `TextFileEditor` не даёт Veil упасть на `libimgui-java64.so` (x86_64).

Отключается только отладочный UI Veil (редактор шейдеров). Сам рендер работает как обычно.

Сборка: залить содержимое в репозиторий GitHub -> Actions -> build -> артефакт `veilfix` -> `veilfix-1.0.0.jar` в папку mods.
