//main file for snack-o-mat

//initiate input before main function
//create constant non-mutable variable
const val fach1 = "Mars"
const val fach2 = "Snickers"

//main function start
fun main(){
    val startAmount = 10
    val kioskView = SnackKioskView()
    val kiosk = Kiosk(kioskView, startAmount)
    val kioskController = SnackKioskController(kiosk)
    kioskView.setController(kioskController)
    kioskView.showMenue(startAmount, startAmount)
