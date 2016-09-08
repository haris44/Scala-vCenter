package services

import java.net.URL;
import org.apache.log4j.Logger;
import com.vmware.vim25.mo._

/**
  * Created by Alexandre on 08/09/2016.
  */
object vCenter {

  def connect(url: String, user: String, pwd: String) : vCenter = {
    val si = new ServiceInstance(new URL(url), user, pwd, true)
    new vCenter(si);
  }
}

case class vCenter(si : ServiceInstance){

  def getRootFolder() = {
    si.getRootFolder
  }

  def getServiceInstance() : ServiceInstance = {
    si
  }

  def getVMs(): List[VirtualMachine] =
    new InventoryNavigator(getRootFolder).searchManagedEntities("VirtualMachine") match {
      case null =>
        List[VirtualMachine]()
      case vms => vms map(_.asInstanceOf[VirtualMachine]) toList
    }

  def getHosts(): List[HostSystem] =

    new InventoryNavigator(getRootFolder).searchManagedEntities("HostSystem") match {
      case null =>
        List[HostSystem]()
      case hosts => hosts map(_.asInstanceOf[HostSystem]) toList

    }

  def getResourcePool(): List[ResourcePool] =
    new InventoryNavigator(getRootFolder()).searchManagedEntities("ResourcePool") match {
      case null =>
        List[ResourcePool]()
      case resource => resource map(_.asInstanceOf[ResourcePool]) toList
    }

  def disconnect() = {
    si.getServerConnection.logout()
  }

}

