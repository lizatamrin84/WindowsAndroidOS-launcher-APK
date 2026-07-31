cmake_minimum_required(VERSION 3.22.1)

project("windowsandroidos_native")

add_library(
    windowsandroidos_native
    SHARED
    native_runtime.cpp
    box64_dxvk_bridge.cpp
)

find_library(
    log-lib
    log
)

target_link_libraries(
    windowsandroidos_native
    ${log-lib}
)
