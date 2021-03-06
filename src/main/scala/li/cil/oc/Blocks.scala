package li.cil.oc

import cpw.mods.fml.common.registry.GameRegistry
import li.cil.oc.common.block._
import li.cil.oc.common.recipe.Recipes
import li.cil.oc.common.tileentity

object Blocks {
  var blockSimple: SimpleDelegator = _
  var blockSimpleWithRedstone: SimpleDelegator = _
  var blockSpecial: SpecialDelegator = _
  var blockSpecialWithRedstone: SpecialDelegator = _

  var robotProxy: RobotProxy = _
  var robotAfterimage: RobotAfterimage = _

  def init() {
    blockSimple = new SimpleDelegator(Settings.get.blockId1)
    blockSimpleWithRedstone = new SimpleRedstoneDelegator(Settings.get.blockId2)
    blockSpecial = new SpecialDelegator(Settings.get.blockId3)
    blockSpecialWithRedstone = new SpecialRedstoneDelegator(Settings.get.blockId4)

    GameRegistry.registerBlock(blockSimple, classOf[Item], Settings.namespace + "simple")
    GameRegistry.registerBlock(blockSimpleWithRedstone, classOf[Item], Settings.namespace + "simple_redstone")
    GameRegistry.registerBlock(blockSpecial, classOf[Item], Settings.namespace + "special")
    GameRegistry.registerBlock(blockSpecialWithRedstone, classOf[Item], Settings.namespace + "special_redstone")

    GameRegistry.registerTileEntity(classOf[tileentity.Adapter], Settings.namespace + "adapter")
    GameRegistry.registerTileEntity(classOf[tileentity.Cable], Settings.namespace + "cable")
    GameRegistry.registerTileEntity(classOf[tileentity.Capacitor], Settings.namespace + "capacitor")
    GameRegistry.registerTileEntity(classOf[tileentity.Case], Settings.namespace + "case")
    GameRegistry.registerTileEntity(classOf[tileentity.Charger], Settings.namespace + "charger")
    GameRegistry.registerTileEntity(classOf[tileentity.DiskDrive], Settings.namespace + "disk_drive")
    GameRegistry.registerTileEntity(classOf[tileentity.Disassembler], Settings.namespace + "disassembler")
    GameRegistry.registerTileEntity(classOf[tileentity.Keyboard], Settings.namespace + "keyboard")
    GameRegistry.registerTileEntity(classOf[tileentity.Hologram], Settings.namespace + "hologram")
    GameRegistry.registerTileEntity(classOf[tileentity.Geolyzer], Settings.namespace + "geolyzer")
    GameRegistry.registerTileEntity(classOf[tileentity.MotionSensor], Settings.namespace + "motion_sensor")
    GameRegistry.registerTileEntity(classOf[tileentity.PowerConverter], Settings.namespace + "power_converter")
    GameRegistry.registerTileEntity(classOf[tileentity.PowerDistributor], Settings.namespace + "power_distributor")
    GameRegistry.registerTileEntity(classOf[tileentity.Redstone], Settings.namespace + "redstone")
    GameRegistry.registerTileEntity(classOf[tileentity.RobotProxy], Settings.namespace + "robot")
    GameRegistry.registerTileEntity(classOf[tileentity.RobotAssembler], Settings.namespace + "robotAssembler")
    GameRegistry.registerTileEntity(classOf[tileentity.Router], Settings.namespace + "router")
    GameRegistry.registerTileEntity(classOf[tileentity.Screen], Settings.namespace + "screen")
    GameRegistry.registerTileEntity(classOf[tileentity.Rack], Settings.namespace + "serverRack")
    GameRegistry.registerTileEntity(classOf[tileentity.WirelessRouter], Settings.namespace + "wireless_router")

    // IMPORTANT: the multi block must come first, since the sub blocks will
    // try to register with it. Also, the order the sub blocks are created in
    // must not be changed since that order determines their actual IDs.
    Recipes.addBlock(new Adapter(blockSimple), "adapter", "oc:adapter")
    Recipes.addBlock(new Cable(blockSpecial), "cable", "oc:cable")
    Recipes.addBlock(new Capacitor(blockSimple), "capacitor", "oc:capacitor")
    Recipes.addBlock(new Case.Tier1(blockSimpleWithRedstone), "case1", "oc:case1")
    Recipes.addBlock(new Case.Tier2(blockSimpleWithRedstone), "case2", "oc:case2")
    Recipes.addBlock(new Case.Tier3(blockSimpleWithRedstone), "case3", "oc:case3")
    Recipes.addBlock(new Charger(blockSimpleWithRedstone), "charger", "oc:charger")
    Recipes.addBlock(new DiskDrive(blockSimple), "diskDrive", "oc:diskDrive")
    Recipes.addBlock(new Keyboard(blockSpecial), "keyboard", "oc:keyboard")
    Recipes.addBlock(new PowerDistributor(blockSimple), "powerDistributor", "oc:powerDistributor")
    Recipes.addBlock(new PowerConverter(blockSimple), "powerConverter", "oc:powerConverter")
    Recipes.addBlock(new Redstone(blockSimpleWithRedstone), "redstone", "oc:redstone")
    robotAfterimage = new RobotAfterimage(blockSpecial)
    robotProxy = Items.registerBlock(new RobotProxy(blockSpecialWithRedstone), "robot")
    Recipes.addBlock(new Switch(blockSimple), "switch", "oc:switch")
    // Copied to simple block for automatic conversion from old format (when
    // screens did not take redstone inputs) to keep save format compatible.
    blockSimple.subBlocks += Recipes.addBlock(new Screen.Tier1(blockSimpleWithRedstone), "screen1", "oc:screen1")
    blockSimple.subBlocks += Recipes.addBlock(new Screen.Tier2(blockSimpleWithRedstone), "screen2", "oc:screen2")
    blockSimple.subBlocks += Recipes.addBlock(new Screen.Tier3(blockSimpleWithRedstone), "screen3", "oc:screen3")

    // v1.2.0
    Recipes.addBlock(new Rack(blockSpecialWithRedstone), "rack", "oc:rack")

    // v1.2.2
    Recipes.addBlock(new Hologram.Tier1(blockSpecial), "hologram1", "oc:hologram1")
    Recipes.addBlock(new AccessPoint(blockSimple), "accessPoint", "oc:accessPoint")

    // v1.2.6
    Items.registerBlock(new Case.TierCreative(blockSimpleWithRedstone), "caseCreative")

    // v1.3.0
    Recipes.addBlock(new Hologram.Tier2(blockSpecial), "hologram2", "oc:hologram2")
    Recipes.addBlock(new Geolyzer(blockSimple), "geolyzer", "oc:geolyzer")
    Recipes.addBlock(new RobotAssembler(blockSpecial), "robotAssembler", "oc:robotAssembler")
    Recipes.addBlock(new Disassembler(blockSimple), "disassembler", "oc:disassembler")

    // ???
    Recipes.addBlock(new MotionSensor(blockSimple), "motionSensor", "oc:motionSensor")
  }
}