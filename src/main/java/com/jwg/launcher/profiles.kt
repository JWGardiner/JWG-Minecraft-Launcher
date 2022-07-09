import javax.swing.*

fun profiles(visible: Boolean) {
    //Default folder launcher/profiles/
    //

    //TODO LIST:
    //TODO: Make users able to change the default profile folder
    //TODO: Let users have custom icons for their profiles

    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())

    val confirm = JButton("Confirm")
    val model = DefaultListModel<String>()
    val gameSoftware = JList(model)

    confirm.setBounds(10, 10, 139, 35)
    gameSoftware.setBounds(10, 55, 140, 215)

    JFrame().also { profileMenu ->
        profileMenu.title = "Profiles"
        profileMenu.setSize(600, 400)
        profileMenu.layout = null
        profileMenu.setLocationRelativeTo(null)
        profileMenu.isVisible = visible
        profileMenu.isResizable = false

        profileMenu.add(confirm)
        profileMenu.add(gameSoftware)


    }
}



