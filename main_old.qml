import QtQuick
// import QtQuick.Window
import QtQuick.Controls
import QtWebView
import QtQuick.Layouts
import QtQuick.Controls.Material
// import QtWebEngine
import QtCore
// import QtQuick.Dialogs
ApplicationWindow {
    id:window
    width: 640
    height: 480
    visible: true
    title: qsTr("UWB")
    property string urls : JSON.stringify([
                                              {name:qsTr("UWillno的博客"),url:"https://uwillno.com"},
                                          ])
    ListView {
        anchors.fill: parent
        model:JSON.parse(urls)
        footer: Button {
            text:"+"
        }

    }

    // Button {
    //     anchors.centerIn: parent
    //     text:"asd"
    //     // onClicked:UWB.startActivity()
    //     // onClicked: UWB.test()
    // }
    // // Material.color:

    // header:
    //     ToolBar {
    //     id:toolBar
    //     height:40
    //     background: Rectangle {
    //         color:"green"
    //         width: parent.width * webView.loadProgress/100
    //     }
    //     Flickable {
    //         width: parent.width
    //         contentWidth: row.implicitWidth
    //         flickDeceleration: Flickable.HorizontalFlick
    //         Row {
    //             id:row
    //             ToolButton{
    //                 text:"后退"
    //                 enabled: webView.canGoBack
    //                 onClicked: {
    //                     webView.goBack()
    //                 }
    //             }
    //             ToolButton{
    //                 text:"前进"
    //                 onClicked: {
    //                     webView.goForward()
    //                 }
    //                 // onClicked: dialog.open()
    //                 // enabled: webView.canGoForward
    //             }
    //             ToolButton{
    //                 text:"刷新"
    //                 onClicked: webView.reload()
    //             }
    //             Label {
    //                 id:label
    //             }
    //             ToolButton{
    //                 text:"清Cookies"
    //                 onClicked: webView.deleteAllCookies()
    //             }
    //             ToolButton{
    //                 id:presetButton
    //                 text:"预设"
    //                 onClicked: presetMenu.open()
    //             }

    //             ToolButton {
    //                 text:qsTr("添加")
    //                 onClicked: {
    //                     presetDialog.open()
    //                 }
    //             }
    //             ToolButton {
    //                 text:qsTr("开始")
    //                 onClicked: {
    //                     toolBar.visible = false
    //                     // presetDialog.open()

    //                 }
    //             }
    //         }
    //     }
    // }
    // Menu {
    //     id:presetMenu
    //     parent:presetButton
    //     Repeater{
    //         id:menuRepeater
    //         model:JSON.parse(urls)
    //         MenuItem{
    //             text:modelData.name
    //             TapHandler{
    //                 onTapped: {
    //                     webView.url = modelData.url
    //                 }
    //                 onLongPressed: {
    //                     if(index!==0){
    //                         const m = JSON.parse(urls)
    //                         m.splice(index, 1)
    //                         urls = JSON.stringify(m)
    //                     }
    //                 }
    //             }
    //         }
    //     }
    // }
    // Settings {
    //     property alias urls: window.urls
    //     property alias currenturl: webView.url
    //     // property name: value

    // }


    Dialog {
        id:presetDialog
        width: parent.width*3/4
        anchors.centerIn: parent
        title: qsTr("预设")

        contentItem: Column {
            id:col
            width: parent.width
            // height:170
            spacing:10
            TextField {
                id:nameField
                width: parent.width
                placeholderText: qsTr("名称")
                // height: 80
            }
            TextField {
                id:urlField
                width: parent.width
                placeholderText: qsTr("链接")
                // height: 80
            }
        }
        standardButtons: Dialog.Ok
        onAccepted:{
            if(nameField.text!=="" && urlField.text!==""){
                const obj = { name: nameField.text,
                    url:urlField.text
                }
                const m = JSON.parse(urls)
                m.push(obj)
                urls = JSON.stringify(m)
                nameField.text=""
                urlField.text=""
            }
        }
    }

    // Timer{
    //     interval: 1000
    //     repeat: true
    //     running: true
    //     onTriggered: console.log(cameraPermission.status)
    // }
    // Component.onCompleted: cameraPermission.request()
    // WebView{
    //     z:0
    //     // visible: false
    //     visible: !(presetMenu.visible || presetDialog.visible)
    //     // visible: true
    //     id:webView
    //     anchors.fill: parent
    //     // url:"https://uwillno.com"
    //     // url:"https://uwillno.com"
    //     // url:"https://ie.icoa.cn/"
    //     settings.allowFileAccess: true
    //     settings.javaScriptEnabled: true
    //     settings.localContentCanAccessFileUrls: true
    //     settings.localStorageEnabled: true
    //     focus:true
    //     Keys.onReleased: event=>{
    //                          // console.log(event.key)
    //                          if(event.key === Qt.Key_Back)
    //                          {
    //                              // // event.accepted = true;
    //                              // backTimer.start()
    //                              // if(webView.canGoBack)
    //                              // webView.goBack()
    //                          }
    //                      }

    //     // Timer {
    //     //     id:backTimer
    //     //     interval: 1000
    //     //     onTriggered: {

    //     //     }
    //     // }
    //     Keys.onPressed: event=>{
    //                         if(event.key === Qt.Key_Back)
    //                         {
    //                             // console.log("asdasd")
    //                             // // // event.accepted = true;
    //                             // // if(webView.canGoBack)
    //                             // // webView.goBack()
    //                         }
    //                     }

    // }

    // onClosing: (close)=>{
    //                close.accepted = false
    //            }
}
