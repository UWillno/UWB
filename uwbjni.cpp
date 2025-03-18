#include "uwbjni.h"
#include "qurl.h"
#include <QStandardPaths>
#include <QFile>
#include <QIODevice>
#include <QDebug>
#include <QCoreApplication>
#include <QtConcurrent>
UWBJNI::UWBJNI(QObject *parent)
    : QObject{parent}
{}

void UWBJNI::startActivity()
{
    // Bundle bundle;
    (void)QtConcurrent::run([=]{
        Bundle bundle;
        String url = QJniObject::fromString("https://bing.com");
        String urlKey = QJniObject::fromString("url");
        String backKey = QJniObject::fromString("back");
        bundle.callMethod<void>("putString",urlKey,url);
        bundle.callMethod<void>("putBoolean",backKey,true);
        String name = QJniObject::fromString("UWillno");
        Intent intent;
        String packageName = QJniObject::fromString("org.uwillno.uwb");
        String className = QJniObject::fromString("org.uwillno.uwb.MainActivity");
        ComponentName component(packageName,className);
        intent.callMethod<Intent>("setComponent",component);
        intent.callMethod<Intent>("putExtra",name,bundle);
        QJniObject context = QNativeInterface::QAndroidApplication::context();
        context.callMethod<void>("startActivity",intent);

    });
    // Intent intent;
    // String packageName = QJniObject::fromString("org.uwillno.uwb");
    // String className = QJniObject::fromString("org.uwillno.uwb.MainActivity");
    // ComponentName component(packageName,className);
    // intent.callMethod<Intent>("setComponent",component);
    // QJniObject context = QNativeInterface::QAndroidApplication::context();
    // context.callMethod<void>("startActivity",intent);


    // File file(QJniObject::fromString("org.uwillno.uwb"));
    // Intent
    // QJniObject myIntent("android/content/Intent","()V");
    // QJniObject packageName = QJniObject::fromString("org.uwillno.uwb");
    // QJniObject className = QJniObject::fromString("org.uwillno.uwb.MainActivity");
    // // jstring
    // // className.object<jstring>()
    // QJniObject component("android/content/ComponentName","(Ljava/lang/String;Ljava/lang/String;)V"
    //                      ,packageName.object<jstring>(),className.object<jstring>());


    // // myIntent.callMethod<jobject>("setComponent",component);
    // myIntent.callObjectMethod("setComponent","(Landroid/content/ComponentName;)Landroid/content/Intent;"
    //                           ,component.object());
    // QJniObject context = QNativeInterface::QAndroidApplication::context();
    // context.callMethod<void>("startActivity","(Landroid/content/Intent;)V",myIntent);
    // // myIntent.callMethod<jobject>("setComponent","(Landroid/content/ComponentName;)Landroid/content/Intent;",component);


}

void UWBJNI::test()
{
    (void)QtConcurrent::run([=]{
        const auto standardPath = QStandardPaths::writableLocation(QStandardPaths::AppDataLocation);
        const auto fileName = standardPath + "/text.html";
        QFile file(fileName);
        file.open(QIODevice::WriteOnly);
        file.write("<h1>Sample text</h1>");
        file.close();
        Bundle bundle;
        String url = QJniObject::fromString(QUrl::fromLocalFile(file.fileName()).toString());
        String urlKey = QJniObject::fromString("url");
        String backKey = QJniObject::fromString("back");
        bundle.callMethod<void>("putString",urlKey,url);
        bundle.callMethod<void>("putBoolean",backKey,true);
        String name = QJniObject::fromString("UWillno");
        Intent intent;
        String packageName = QJniObject::fromString("org.uwillno.uwb");
        String className = QJniObject::fromString("org.uwillno.uwb.MainActivity");
        ComponentName component(packageName,className);
        intent.callMethod<Intent>("setComponent",component);
        intent.callMethod<Intent>("putExtra",name,bundle);
        QJniObject context = QNativeInterface::QAndroidApplication::context();
        context.callMethod<void>("startActivity",intent);

    });


    // qDebug() <<
}

void UWBJNI::startWebView(const QString url, const bool back)
{
    (void)QtConcurrent::run([=]{
        const auto standardPath = QStandardPaths::writableLocation(QStandardPaths::AppDataLocation);
        const auto fileName = standardPath + "/text.html";
        QFile file(fileName);
        file.open(QIODevice::WriteOnly);
        file.write("<h1>Sample text</h1>");
        file.close();
        Bundle bundle;
        String url1 = QJniObject::fromString(url);
        String urlKey = QJniObject::fromString("url");
        String backKey = QJniObject::fromString("back");
        bundle.callMethod<void>("putString",urlKey,url1);
        bundle.callMethod<void>("putBoolean",backKey,back);
        String name = QJniObject::fromString("UWillno");
        Intent intent;
        String packageName = QJniObject::fromString("org.uwillno.uwb");
        String className = QJniObject::fromString("org.uwillno.uwb.MainActivity");
        ComponentName component(packageName,className);
        intent.callMethod<Intent>("setComponent",component);
        intent.callMethod<Intent>("putExtra",name,bundle);
        QJniObject context = QNativeInterface::QAndroidApplication::context();
        context.callMethod<void>("startActivity",intent);
    });
}
