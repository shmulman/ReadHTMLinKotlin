package il.co.shmulman.www.showhtml_v1

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ConnectWifi.setOnClickListener() {
            ConnectionStatus.text = "Loading sensors ..."

            doAsync{
                val response200 = URL("http://10.100.102.200/").readText()
                uiThread {
                    printOutput(response200,"200")
                }
                val response201 = URL("http://10.100.102.201/").readText()
                uiThread {
                    printOutput(response201,"201")
                }
                ConnectionStatus.text = "All sensors are loaded"
            }
        }
        DisconnectWifi.setOnClickListener() {
            ConnectionStatus.text = "Stopped"
        }
    }

    private fun printOutput(response : String, sensorNumber : String) {
        val timeAndDate = response.substringAfter("<h1>").substringBefore("<br>")
        val temperature = response.substringAfter("Temp").substringBefore(" C ")
        DataOutput.append("$sensorNumber: ${timeAndDate.dropLast(1)} Temperature: $temperature\n")
        Temperature_Final.text = temperature + "\u00B0"
    }
}
