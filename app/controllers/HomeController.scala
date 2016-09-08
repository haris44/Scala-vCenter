package controllers

import javax.inject._
import com.vmware.vim25.mo._
import play.api.libs.json.Json
import play.api.mvc._
import services.vCenter


@Singleton
class HomeController extends Controller {

  def index = Action {

    val connexion : vCenter = vCenter.connect("https://url/sdk", "login", "pwd")

    val VMs : List[VirtualMachine] = connexion.getVMs()
    val Hosts : List[HostSystem] = connexion.getHosts()

    // Je filtre les VMS qui ne renvoie pas d'iP (pas de VMTools), et je creer un nouveau tableau avec les adresses IP
    val ipVMs : List[String] = VMs filter (element => element.getGuest.getIpAddress != null) map (element => element.getGuest.getIpAddress)
    val ipHosts : List[String] = Hosts map (element => element.getName)

    connexion.disconnect()

    Ok(Json.toJson(ipVMs ++ ipHosts))

  }

}




