cmake_minimum_required(VERSION 3.22.1)
project("app")

add_library(gif STATIC
        ${CMAKE_CURRENT_SOURCE_DIR}/lib/platform_external_giflib/dgif_lib.c
        ${CMAKE_CURRENT_SOURCE_DIR}/lib/platform_external_giflib/egif_lib.c
        ${CMAKE_CURRENT_SOURCE_DIR}/lib/platform_external_giflib/gifalloc.c
        ${CMAKE_CURRENT_SOURCE_DIR}/lib/platform_external_giflib/gif_err.c
        ${CMAKE_CURRENT_SOURCE_DIR}/lib/platform_external_giflib/gif_hash.c
        ${CMAKE_CURRENT_SOURCE_DIR}/lib/platform_external_giflib/quantize.c
)
target_include_directories(gif
        PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/lib/platform_external_giflib
)
target_compile_options(gif PRIVATE
        -Werror
        -Wno-format
        -Wno-sign-compare
        -Wno-unused-parameter
        -DHAVE_REALLOCARRAY
)

add_library(giftranscode SHARED
    ${CMAKE_CURRENT_SOURCE_DIR}/jni/GifTranscoder.cpp)

target_link_libraries(giftranscode gif log dl)

target_compile_options(giftranscode PRIVATE
        -Wno-unused-parameter
        -Wno-switch
)

add_library(framesequence SHARED
        ${CMAKE_CURRENT_SOURCE_DIR}/lib/platform_frameworks_ex/framesequence/jni/BitmapDecoderJNI.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/lib/platform_frameworks_ex/framesequence/jni/FrameSequence.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/lib/platform_frameworks_ex/framesequence/jni/FrameSequenceJNI.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/lib/platform_frameworks_ex/framesequence/jni/FrameSequence_gif.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/lib/platform_frameworks_ex/framesequence/jni/JNIHelpers.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/lib/platform_frameworks_ex/framesequence/jni/Registry.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/lib/platform_frameworks_ex/framesequence/jni/Stream.cpp
)

target_link_libraries(framesequence gif jnigraphics log)

target_compile_options(framesequence PRIVATE
        -Wall
        -Werror
        -Wno-unused-parameter
        -Wno-unused-but-set-variable
        -Wno-unused-variable
        -Wno-overloaded-virtual
        -Wno-vla-cxx-extension
        -fvisibility=hidden
)
