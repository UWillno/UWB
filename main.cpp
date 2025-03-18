#include "uwbjni.h"
#include <QGuiApplication>
#include <QQmlApplicationEngine>
#include <QQmlContext>
// #include <QJniObject>
// #include <QtWebView>
int main(int argc, char *argv[])
{
#if QT_VERSION < QT_VERSION_CHECK(6, 0, 0)
    QCoreApplication::setAttribute(Qt::AA_EnableHighDpiScaling);
#endif
    QGuiApplication app(argc, argv);
    // QtWebView::initialize();
    // QtWebEngineQuick::initialize();
    QQmlApplicationEngine engine;
    app.setOrganizationName("UWlab");
    app.setOrganizationDomain("uwillno.com");
    app.setApplicationName("UWB");

    const QUrl url(QStringLiteral("qrc:/main.qml"));
    QObject::connect(
        &engine,
        &QQmlApplicationEngine::objectCreated,
        &app,
        [url](QObject *obj, const QUrl &objUrl) {
            if (!obj && url == objUrl)
                QCoreApplication::exit(-1);
        },
        Qt::QueuedConnection);
    UWBJNI uwb;
    engine.rootContext()->setContextProperty("UWB",&uwb);
    engine.load(url);



    // UWBJNI JNI;
    // JNI.startActivity();

    return app.exec();
}
