package at.helpch.papi.expansion.player;

import at.helpch.placeholderapi.PlaceholderAPIPlugin;
import at.helpch.placeholderapi.expansion.PlaceholderExpansion;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.modules.entitystats.asset.DefaultEntityStatTypes;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

public final class PlayerExpansion extends PlaceholderExpansion {
  public String getIdentifier() {
    return "hyPlayer";
  }
  
  public String getAuthor() {
    return "HelpChat";
  }
  
  public String getVersion() {
    return "1.0.2";
  }
  
  public String onPlaceholderRequest(PlayerRef player, String identifier) {
    if (player == null)
      return ""; 
    
    switch (identifier) {
      case "uuid":
        return player.getUuid().toString();
      case "username":
        return player.getUsername();
      case "language":
        return player.getLanguage();
      case "world_uuid":
        return player.getWorldUuid().toString();
      case "x":
        return String.valueOf(player.getTransform().getPosition().getX());
      case "y":
        return String.valueOf(player.getTransform().getPosition().getY());
      case "z":
        return String.valueOf(player.getTransform().getPosition().getZ());
      case "yaw":
        return String.valueOf(player.getHeadRotation().getYaw());
      case "pitch":
        return String.valueOf(player.getHeadRotation().getPitch());
    } 

    Ref<EntityStore> ref = player.getReference();

    if (ref == null || !ref.isValid())
      return null; 

    Store<EntityStore> store = ref.getStore();
    Player p = (Player)store.getComponent(ref, Player.getComponentType());
    EntityStatMap stats = (EntityStatMap)store.getComponent(ref, EntityStatMap.getComponentType());

    if (p == null)
      return null; 

    switch (identifier) {
      case "has_played_before":
        return bool(!p.isFirstSpawn());
      case "name":
        return p.getDisplayName();
      case "gamemode":
        return p.getGameMode().name();
      case "world":
        return p.getWorld().getName();
      case "biome":
        return p.getWorldMapTracker().getCurrentBiomeName();
      case "item_in_hand":
        return String.valueOf(p.getInventory().getActiveHotbarItem().getItemId());
      case "item_in_hand_quantity":
        return String.valueOf(p.getInventory().getActiveHotbarItem().getQuantity());
      case "item_in_hand_durability":
        return String.valueOf(p.getInventory().getActiveHotbarItem().getDurability());
      case "item_in_hand_broken":
        return bool(p.getInventory().getActiveHotbarItem().isBroken());
      case "item_in_hand_unbreakable":
        return bool(p.getInventory().getActiveHotbarItem().isUnbreakable());
      case "current_fall_distance":
        return String.valueOf(p.getCurrentFallDistance());
      case "view_radius":
        return String.valueOf(p.getViewRadius());
      case "client_view_radius":
        return String.valueOf(p.getClientViewRadius());
      case "since_last_spawn_nanos":
        return String.valueOf(p.getSinceLastSpawnNanos());
      case "mount_entity_id":
        return String.valueOf(p.getMountEntityId());
      case "is_collidable":
        return bool(p.isCollidable());
      case "health":
        return String.valueOf(stats.get(DefaultEntityStatTypes.getHealth()).get());
      case "health_max":
        return String.valueOf(stats.get(DefaultEntityStatTypes.getHealth()).getMax());
      case "health_min":
        return String.valueOf(stats.get(DefaultEntityStatTypes.getHealth()).getMin());
      case "ammo":
        return String.valueOf(stats.get(DefaultEntityStatTypes.getAmmo()).get());
      case "ammo_max":
        return String.valueOf(stats.get(DefaultEntityStatTypes.getAmmo()).getMax());
      case "ammo_min":
        return String.valueOf(stats.get(DefaultEntityStatTypes.getAmmo()).getMin());
      case "stamina":
        return String.valueOf(stats.get(DefaultEntityStatTypes.getStamina()).get());
      case "stamina_max":
        return String.valueOf(stats.get(DefaultEntityStatTypes.getStamina()).getMax());
      case "stamina_min":
        return String.valueOf(stats.get(DefaultEntityStatTypes.getStamina()).getMin());
      case "mana":
        return String.valueOf(stats.get(DefaultEntityStatTypes.getMana()).get());
      case "mana_max":
        return String.valueOf(stats.get(DefaultEntityStatTypes.getMana()).getMax());
      case "mana_min":
        return String.valueOf(stats.get(DefaultEntityStatTypes.getMana()).getMin());
      case "oxygen":
        return String.valueOf(stats.get(DefaultEntityStatTypes.getOxygen()).get());
      case "oxygen_max":
        return String.valueOf(stats.get(DefaultEntityStatTypes.getOxygen()).getMax());
      case "oxygen_min":
        return String.valueOf(stats.get(DefaultEntityStatTypes.getOxygen()).getMin());
      case "signature_energy":
        return String.valueOf(stats.get(DefaultEntityStatTypes.getSignatureEnergy()).get());
      case "signature_energy_max":
        return String.valueOf(stats.get(DefaultEntityStatTypes.getSignatureEnergy()).getMax());
      case "signature_energy_min":
        return String.valueOf(stats.get(DefaultEntityStatTypes.getSignatureEnergy()).getMin());
    } 
    if (identifier.startsWith("has_permission_")) {
      if ((identifier.split("has_permission_")).length > 1) {
        String perm = identifier.split("has_permission_")[1];
        return bool(p.hasPermission(perm));
      } 
      return bool(false);
    } 
    return null;
  }
  
  public String bool(boolean b) {
    return b ? PlaceholderAPIPlugin.instance().configManager().config().booleanValue().trueValue() : PlaceholderAPIPlugin.instance().configManager().config().booleanValue().falseValue();
  }
}