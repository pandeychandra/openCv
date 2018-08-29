#include <jni.h>
#include <string>


JNICALL
Java_com_example_moonlight_opencvcamera1_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
