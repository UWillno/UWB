#ifndef UWBJNI_H
#define UWBJNI_H

#include <QObject>
#include <qjnitypes.h>
// #include <QtJniTypes>

using namespace QtJniTypes;
Q_DECLARE_JNI_CLASS(Bundle,"android/os/Bundle");
Q_DECLARE_JNI_CLASS(ComponentName,"android/content/ComponentName");
class UWBJNI : public QObject
{
    Q_OBJECT
public:
    explicit UWBJNI(QObject *parent = nullptr);


   Q_INVOKABLE void startActivity();

   Q_INVOKABLE void test();

   Q_INVOKABLE void startWebView(const QString url,const bool back);

signals:
};

#endif // UWBJNI_H
