import java.util.*
import javax.naming.Name

//main file for snack-o-mat
//i know files could be written in separate classes

//initiate input before main function
//create constant non-mutable variables
const val fach1 = "Mars";
const val fach2 = "Snickers";
const val fach3 = "leer";


//main function start
fun main() {
    val startAmount = 10;
    val kioskView = SnackKioskView();
    val kiosk = Kiosk(kioskView, startAmount);
    val kioskController = SnackKioskController(kiosk);
    kioskView.setController(kioskController);
    kioskView.showMenu(startAmount, startAmount);
}
class SnackKioskView {
    private lateinit var controller: SnackKioskController;
    fun setController(controller: SnackKioskController) {
        this.controller = controller;
    }
    //show interface menu for customer
    fun showMenu(amountOfSnickers:Int, amountOfMars:Int) {
        println(
            """
                | Select:
                | 1 - $fach1
                | 2 - $fach2
                | 8 - Fach1 nachfüllen
                | 9 - Fach2 nachfüllen
            """.trimIndent()
        );
        askForCommand();
    }
    fun showNotEnougChocolateBarAvailable(chocolateBarName: String, amountOfSnickers:Int, amountOfMars:Int) {
        if(amountOfMars == 0 && fach1 == chocolateBarName){
            print("$chocolateBarName not available");
        }
        if (amountOfSnickers == 0 && fach2 == chocolateBarName){
            print("$chocolateBarName not available");
        }
    }
    private fun askForCommand() {
        val reader = Scanner(System.`in`);
        var command: String;
        do {
            command = reader.nextLine();
        }
        while (controller.parse(command));
    }
}
class SnackKioskController(val kiosk: Kiosk) {
    fun parse(command:Int) {
        return when (parse(command)){
            1 -> {
                kiosk.sellMars();
            }
            2 -> {
                kiosk.sellSnickers();
            }
            8 -> {
                return kiosk.refillFach(chocolateBarName = "Mars",amount = 10)
            }
            9 -> {
                return kiosk.refillFach(chocolateBarName = "Snickers",amount = 10)
            }
            else -> print("Please enter 1,2,8 or 9");
        }
    }
}
class Kiosk(val kioskView: SnackKioskView, startAmount: Int = 0) {
    val repository = ChocolateBarRepository();
    init {
        fillinStock("Snickers", startAmount);
        fillinStock("Mars", startAmount);
    }
    private fun fillinStock(chocolateBarName:String, amount: Int) {
        for (i in 1..amount) {
            repository.createChocolateBar(chocolateBarName);
        }
    }
    fun sellSnickers() {
        removeBar(fach1);
    }
    fun sellMars() {
        removeBar(fach2);
    }
    private fun removeBar(chocolateBarName:String){
        repository.removeChocolateBar(chocolateBarName(Name))
        //erweiterbar, evtl. durch iterieren für mehr Fächer
        kioskView.showMenu(repository.getChocolateBarSize(fach1),repository.getChocolateBarSize(fach2));
        kioskView.showNotEnougChocolateBarAvailable(chocolateBarName,repository.getChocolateBarSize(fach1),repository.getChocolateBarSize(fach2));
    }
    fun refillFach(chocolateBarName: String, amount: Int){
        for (i in 1..amount){
            repository.createChocolateBar(chocolateBarName)
        }
    }
}
class ChocolateBarRepository {
    private val chocolateBarStock = mutableListOf<ChocolateBar>()
    fun createChocolateBar(name:String) {
        chocolateBarStock.add(ChocolateBar(name))
    }
    fun removeChocolateBar(chocolateBar: String) {
        chocolateBarStock.remove(chocolateBar)
    }
    fun getChocolateBarSize(chocolateBarName: String) : Int {
        return chocolateBarName.count().toInt();
    }
}
data class ChocolateBar(val name: String)
