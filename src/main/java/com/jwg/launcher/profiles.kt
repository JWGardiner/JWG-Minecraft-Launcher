import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.io.File
import javax.swing.*


fun profiles(visible: Boolean) {
    //Default folder launcher/profiles/

    //TODO LIST:
    //TODO: Make users able to change the default profile folder
    //TODO: Let users have custom icons for their profiles

    //New profile options
    var gameType: String
    var gameName: String
    var gameVer: String

    //Configurable options
    val gameTypes = "launcher/templates"
    val profiles = "launcher/profiles"

    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
    val listOfGameTypes: Array<out File>? = File(gameTypes).listFiles(); var j = 0; var k = 0
    for (i in listOfGameTypes!!) {
        j += 1
    }
    val listOfGameTypesF = arrayOfNulls<String>(j)
    while (k < j) {
        listOfGameTypesF[k] = listOfGameTypes[k].name
        k += 1
    }

    val confirm = JButton("Confirm")
    val name = JTextField("Name")

    val versionScrollPane = JScrollPane()
    val versionsModel = DefaultListModel<String>()
    val versionList = JList(versionsModel)
    versionScrollPane.setViewportView(versionList)
    versionScrollPane.setBounds(160, 55, 140, 305)
    versionList.setBounds(160, 55, 140, 305)

    val gameSoftwareScrollPane = JScrollPane()
    val gameSoftwareModel = DefaultListModel<String>()
    val gameSoftwareList = JList(gameSoftwareModel)
    gameSoftwareList.setListData(listOfGameTypesF)
    gameSoftwareScrollPane.setViewportView(gameSoftwareList)
    gameSoftwareScrollPane.setBounds(10, 55, 140, 305)
    gameSoftwareList.setBounds(10, 55, 140, 305)

    confirm.setBounds(10, 10, 139, 35)
    name.setBounds(160, 10, 139, 35)

    JFrame().also { profileMenu ->
        profileMenu.title = "Profiles"
        profileMenu.setSize(310, 400)
        profileMenu.layout = null
        profileMenu.setLocationRelativeTo(null)
        profileMenu.isVisible = visible
        profileMenu.isResizable = false

        profileMenu.add(confirm)
        profileMenu.add(name)
        profileMenu.add(gameSoftwareScrollPane)
        profileMenu.add(versionScrollPane)

        val gsMouseListener: MouseListener = object : MouseAdapter() {
            override fun mouseClicked(mouseEvent: MouseEvent) {
                gameType = gameSoftwareList.selectedValue.toString()

                val listOfVersionTypes: Array<out File>? = File("$gameTypes/$gameType/").listFiles(); j = 0; k = 0
                for (i in listOfVersionTypes!!) {
                    j += 1
                }
                val listOfVersionTypesF = arrayOfNulls<String>(j)
                while (k < j) {
                    listOfVersionTypesF[k] = listOfVersionTypes[k].name
                    k += 1
                }

                versionList.setListData(listOfVersionTypesF)
                versionList.updateUI()

            }
        }
        gameSoftwareList.addMouseListener(gsMouseListener);
        val verMouseListener: MouseListener = object : MouseAdapter() {
            override fun mouseClicked(mouseEvent: MouseEvent) {
                gameVer = versionList.selectedValue.toString()
            }
        }
        confirm.addActionListener {

        }
    }
}



