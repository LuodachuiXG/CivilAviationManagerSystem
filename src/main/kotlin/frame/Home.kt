package frame

import javax.swing.JFrame

class Home: JFrame {
    constructor(): super("Hello") {
        setLocation(100, 100)
        setSize(400, 300)
        defaultCloseOperation = EXIT_ON_CLOSE
        isVisible = true
    }
}