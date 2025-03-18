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
    Material.accent: Material.Blue
    Material.theme: Material.System
    Material.primary: Material.Teal
    Settings {
        property alias urls: window.urls
    }

    property string urls : JSON.stringify([
                                              {name:qsTr("UWillno的博客"),url:"https://uwillno.com",back:false}
                                          ])
    ListView {
        id:listview
        anchors.fill: parent
        anchors.margins: 10
        model:JSON.parse(urls)
        delegate:RowLayout {
            width:listview.width
            Label {
                Layout.fillWidth: true
                text:modelData.name
            }
            Button {
                text:"启动"
                onClicked: UWB.startWebView(modelData.url,modelData.back)
            }
            Button {
                visible: index!==0
                text:"删除"
                onClicked: {
                    if(index!==0){
                        const m = JSON.parse(urls)
                        m.splice(index, 1)
                        urls = JSON.stringify(m)
                    }
                }
            }
        }
        footer: Button {
            width: parent.width
            text:"+"
            onClicked: presetDialog.open()
        }

    }


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
            CheckBox {
                id:backCheckBox
                checked: false
                text:"禁用后退"
            }
        }
        standardButtons: Dialog.Ok
        onAccepted:{
            if(nameField.text!=="" && urlField.text!==""){
                const obj = { name: nameField.text,
                    url:urlField.text,
                    back:backCheckBox.checked
                }
                const m = JSON.parse(urls)
                m.push(obj)
                urls = JSON.stringify(m)
                nameField.text=""
                urlField.text=""
                backCheckBox.checked = true
            }
        }
    }

}
