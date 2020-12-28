package de.thomas.utils.builder;

import com.destroystokyo.paper.inventory.meta.ArmorStandMeta;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.simple.JSONObject;

import java.lang.reflect.Field;
import java.util.*;


@SuppressWarnings({"UnusedReturnValue", "unused", "deprecation"})
public class ItemBuilder {

    /**
     * Vars
     */
    private Inventory inventory;
    private ItemStack itemStack;
    private ItemMeta itemMeta;
    private SkullMeta skullMeta;
    private PotionMeta potionMeta;

    private double position = -1;

    /**
     * init ItemBuilder without argument
     */
    public ItemBuilder() {
        this(Material.AIR);
    }

    /**
     * init ItemBuilder
     */
    public ItemBuilder(@NotNull Material material) {
        this(material, 1);
    }

    /**
     * init ItemBuilder
     */
    public ItemBuilder(@NotNull Material material, int amount) {
        this(material, amount, 0);
    }

    /**
     * init ItemBuilder
     */
    public ItemBuilder(@NotNull Material material, int amount, int data) {
        this.itemStack = new ItemStack(material, amount, (byte) data);
        this.itemMeta = itemStack.getItemMeta();
    }

    /**
     * init ItemBuilder from his json object
     */
    public ItemBuilder(@NotNull JSONObject jsonObject) {
        String string = jsonObject.get("serialized").toString();
        this.itemStack = fromString(string).toItemStack();
        this.itemMeta = itemStack.getItemMeta();
        this.position = fromString(string).getPosition();
    }

    /**
     * init ItemBuilder
     */
    public ItemBuilder(@NotNull ItemStack itemStack) {
        this.itemStack = itemStack;
        this.itemMeta = itemStack.getItemMeta();
    }

    /**
     * Set item
     */
    public @NotNull ItemBuilder setItem(@NotNull Material material) {
        this.itemStack.setType(material);
        return this;
    }

    /**
     * Set ItemStack
     */
    public @NotNull ItemBuilder setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
        return this;
    }

    /**
     * set this.inventory value
     */
    public @NotNull ItemBuilder inventory(Inventory inventory) {
        this.inventory = inventory;
        return this;
    }

    /**
     * set the display name of the item
     */
    public @NotNull ItemBuilder setName(String name) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);
        this.itemMeta = itemMeta;
        return this;
    }

    /**
     * Add lore from String list
     */
    public @NotNull ItemBuilder addLore(List<String> lores) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(lores);
        itemStack.setItemMeta(itemMeta);
        this.itemMeta = itemMeta;
        return this;
    }

    /**
     * Add lore from String...
     */
    public @NotNull ItemBuilder addLore(String... lores) {
        addLore(Arrays.asList(lores));
        return this;
    }

    /**
     * add enchant to the item
     */
    public @NotNull ItemBuilder addEnchantment(@NotNull Enchantment enchantment, int value, boolean ignoreLevelRestriction) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.addEnchant(enchantment, value, ignoreLevelRestriction);
        this.itemStack.setItemMeta(itemMeta);
        this.itemMeta = itemMeta;
        return this;
    }

    /**
     * add enchants from map (use for json object)
     */
    public @NotNull ItemBuilder setEnchants(@NotNull Map<Enchantment, Integer> enchantment) {
        for (Map.Entry<Enchantment, Integer> entry : enchantment.entrySet()) {
            Enchantment enchant = entry.getKey();
            addEnchantment(enchant, entry.getValue(), entry.getValue() > enchant.getMaxLevel());
        }
        return this;
    }

    /**
     * Remove an enchantment on the item
     */
    public @NotNull ItemBuilder removeEnchant(@NotNull Enchantment enchantment) {
        if (!this.getEnchantments().containsKey(enchantment))
            return this;
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.removeEnchant(enchantment);
        this.itemStack.setItemMeta(itemMeta);
        this.itemMeta = itemMeta;
        return this;
    }

    /**
     * remove all enchant on item from a list
     */
    public @NotNull ItemBuilder removeEnchants(@NotNull List<Enchantment> enchantments) {
        for (Enchantment enchantment : enchantments) {
            if (!this.getEnchantments().containsKey(enchantment))
                continue;
            this.removeEnchant(enchantment);
        }
        return this;
    }

    /**
     * remove all enchantment on the item
     */
    public @NotNull ItemBuilder clearEnchants() {
        if (this.getEnchantments() == null)
            return this;
        for (Enchantment enchantment : this.getEnchantments().keySet())
            this.removeEnchant(enchantment);
        return this;
    }

    /**
     * add ItemFlag on your item
     */
    public @NotNull ItemBuilder addItemFlag(ItemFlag itemFlag) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.addItemFlags(itemFlag);
        itemStack.setItemMeta(itemMeta);
        this.itemMeta = itemMeta;
        return this;
    }

    /**
     * add ItemFlags on your item
     */
    public @NotNull ItemBuilder addItemFlag(ItemFlag... itemFlag) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.addItemFlags(itemFlag);
        itemStack.setItemMeta(itemMeta);
        this.itemMeta = itemMeta;
        return this;
    }

    /**
     * add ItemFlags on your item from ItemFlag list
     */
    public @NotNull ItemBuilder addItemFlag(@NotNull List<ItemFlag> itemFlag) {
        itemFlag.forEach(this::addItemFlag);
        return this;
    }

    /**
     * remove ItemFlag on your item
     */
    public @NotNull ItemBuilder removeItemFlag(ItemFlag itemFlag) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.removeItemFlags(itemFlag);
        itemStack.setItemMeta(itemMeta);
        this.itemMeta = itemMeta;
        return this;
    }

    /**
     * hide enchant
     */
    public @NotNull ItemBuilder hideEnchant() {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(itemMeta);
        this.itemMeta = itemMeta;
        return this;
    }

    /**
     * show enchant
     */
    public @NotNull ItemBuilder showEnchant() {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(itemMeta);
        this.itemMeta = itemMeta;
        return this;
    }

    /**
     * Set durrability of item
     */
    public @NotNull ItemBuilder setNewDurability(int durability) {
        itemStack.setDurability((short) durability);
        return this;
    }

    /**
     * If your item is a player skull you can apply a special player skull texture
     */
    public @NotNull ItemBuilder setSkullTextureFromePlayerName(String playerName) {
        this.skullMeta = (SkullMeta) itemStack.getItemMeta();
        this.skullMeta.setOwner(playerName);
        itemStack.setItemMeta(skullMeta);
        return this;
    }

    /**
     * If your item is a player skull you can apply a special player skull texture
     */
    public @NotNull ItemBuilder setSkullTexture(@NotNull Player player) {
        setSkullTextureFromePlayerName(player.getName());
        return this;
    }

    /**
     * If your item is a player skull you can apply a texture
     * value is the base64 value of the skull texture
     * You can find the value on https://minecraft-heads.com
     */
    public @NotNull ItemBuilder setSkullTexture(String value) {
        this.skullMeta = (SkullMeta) itemStack.getItemMeta();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), null);
        gameProfile.getProperties().put("textures", new Property("textures", value));

        try {
            Field gameProfileField = skullMeta.getClass().getDeclaredField("profile");
            gameProfileField.setAccessible(true);
            gameProfileField.set(skullMeta, gameProfile);
        } catch (@NotNull IllegalAccessException | NoSuchFieldException error) {
            error.printStackTrace();
        }

        itemStack.setItemMeta(skullMeta);
        return this;
    }

    /**
     * apply potion effect type on the potion bottle
     */
    public @NotNull ItemBuilder addPotionEffect(@NotNull PotionEffectType potionEffectType) {
        addPotionEffect(potionEffectType, 10);
        return this;
    }

    /**
     * apply potion effect type with duration on the potion bottle
     */
    public @NotNull ItemBuilder addPotionEffect(@NotNull PotionEffectType potionEffectType, int duration) {
        addPotionEffect(potionEffectType, duration, 1);
        return this;
    }

    /**
     * apply potion effect type with duration and level on the potion bottle
     */
    public @NotNull ItemBuilder addPotionEffect(@NotNull PotionEffectType potionEffectType, int duration, int amplifier) {
        addPotionEffect(potionEffectType, duration, amplifier, true);
        return this;
    }

    /**
     * apply potion effect type with duration, level and ambiance option on the potion bottle
     */
    public @NotNull ItemBuilder addPotionEffect(@NotNull PotionEffectType potionEffectType, int duration, int amplifier, boolean ambient) {
        addPotionEffect(potionEffectType, duration, amplifier, ambient, false);
        return this;
    }

    /**
     * apply potion effect type with duration, level and ambiance option on the potion bottle, can make this effect to overwrite
     */
    public @NotNull ItemBuilder addPotionEffect(@NotNull PotionEffectType potionEffectType, int duration, int amplifier, boolean ambient, boolean overwrite) {
        this.potionMeta = (PotionMeta) this.itemStack.getItemMeta();
        this.potionMeta.addCustomEffect(new PotionEffect(potionEffectType, duration, amplifier, ambient), overwrite);
        this.itemStack.setItemMeta(this.potionMeta);
        return this;
    }

    /**
     * remove specific potion effect type
     */
    public @NotNull ItemBuilder removePotionEffect(@NotNull PotionEffectType potionEffectType) {
        this.potionMeta = (PotionMeta) this.itemStack.getItemMeta();
        if (this.potionMeta == null || !this.potionMeta.hasCustomEffect(potionEffectType))
            return this;
        this.potionMeta.removeCustomEffect(potionEffectType);
        this.itemStack.setItemMeta(potionMeta);
        return this;
    }

    /**
     * remove all potion effect from a list
     */
    public @NotNull ItemBuilder removePotionEffect(@NotNull List<PotionEffectType> potionEffectTypes) {
        for (PotionEffectType potionEffectType : potionEffectTypes) {
            if (this.potionMeta == null || !this.potionMeta.hasCustomEffect(potionEffectType))
                continue;
            removePotionEffect(potionEffectType);
        }
        return this;
    }

    /**
     * clear potion effect on item
     */
    public @NotNull ItemBuilder clearPotionEffect() {
        if (this.getPotionEffects() == null)
            return this;
        for (PotionEffect potionEffect : this.getPotionEffects()) {
            removePotionEffect(potionEffect.getType());
        }
        return this;
    }

    /**
     * set potion type on the potion
     */
    public @NotNull ItemBuilder setPotion(@NotNull PotionType potionType) {
        setPotion(potionType, true);
        return this;
    }

    /**
     * set potion type on the item with splash option
     */
    public @NotNull ItemBuilder setPotion(@NotNull PotionType potionType, boolean splash) {
        Potion potion = new Potion(PotionType.WATER);
        potion.setSplash(splash);
        potion.setType(potionType);
        potion.apply(this.itemStack);
        return this;
    }

    /**
     * Inject item in inventory
     */
    public @NotNull ItemBuilder inject(@NotNull Inventory inventory, int position) {
        inventory.setItem(position, toItemStack());
        return this;
    }

    /**
     * Inject item in inventory
     */
    public @NotNull ItemBuilder inject(@NotNull Inventory inventory) {
        inventory.addItem(toItemStack());
        return this;
    }

    /**
     * Inject item in inventory
     */
    public @NotNull ItemBuilder inject(int position) {
        inventory.setItem(position, toItemStack());
        return this;
    }

    /**
     * Inject item in inventory
     */
    public @NotNull ItemBuilder inject() {
        this.inventory.addItem(toItemStack());
        return this;
    }

    /**
     * Open inventory to the player
     */
    public void open(@NotNull Player player) {
        player.openInventory(inventory);
    }

    /**
     * get position
     */
    public long getPosition() {
        return (long) this.position;
    }

    /**
     * Set the position of the item
     */
    public @NotNull ItemBuilder setPosition(int position) {
        this.position = position;
        return this;
    }

    /**
     * build item
     */
    public ItemStack toItemStack() {
        return itemStack;
    }

    /**
     * @param itemBuilder returns if two item builder are similar
     *                    This method compare type, data, and display name of items
     */
    public boolean isSimilar(@NotNull ItemBuilder itemBuilder) {
        return hasSameMaterial(itemBuilder) && hasSameData(itemBuilder) && hasSameDisplayName(itemBuilder);
    }

    /**
     * @param itemBuilder returns if two item builder are exactly same
     *                    This method compare all parameter of items
     */
    public boolean isExactlySame(@NotNull ItemBuilder itemBuilder) {
        return hasSameMaterial(itemBuilder) && hasSameData(itemBuilder) && hasSameDisplayName(itemBuilder)
                && hasSameAmount(itemBuilder) && hasSameDurability(itemBuilder) && hasSameEnchantment(itemBuilder)
                && hasSameItemFlag(itemBuilder) && hasSameLore(itemBuilder) && hasSameBreakableStat(itemBuilder);
    }

    /**
     * @param itemBuilder returns if two item builder has same type
     */
    public boolean hasSameMaterial(@NotNull ItemBuilder itemBuilder) {
        return getMaterial() == itemBuilder.getMaterial();
    }

    /**
     * @param itemBuilder returns if two item builder has same display name
     */
    public boolean hasSameDisplayName(@NotNull ItemBuilder itemBuilder) {
        return getDisplayName().equalsIgnoreCase(itemBuilder.getDisplayName());
    }

    /**
     * @param itemBuilder returns if two item builder has same enchantments
     */
    public boolean hasSameEnchantment(@NotNull ItemBuilder itemBuilder) {
        return getEnchantments().equals(itemBuilder.getEnchantments());
    }

    /**
     * @param itemBuilder returns if two item builder has same item flags
     */
    public boolean hasSameItemFlag(@NotNull ItemBuilder itemBuilder) {
        return getItemFlag().equals(itemBuilder.getItemFlag());
    }

    /**
     * @param itemBuilder returns if two item builder has same lore
     */
    public boolean hasSameLore(@NotNull ItemBuilder itemBuilder) {
        if (getLore() == null)
            return false;
        return getLore().equals(itemBuilder.getLore());
    }

    /**
     * @param itemBuilder returns if two item builder has same data
     */
    public boolean hasSameData(@NotNull ItemBuilder itemBuilder) {
        return getData() == itemBuilder.getData();
    }

    /**
     * @param itemBuilder returns if two item builder has same amount
     */
    public boolean hasSameAmount(@NotNull ItemBuilder itemBuilder) {
        return getAmount() == itemBuilder.getAmount();
    }

    /**
     * @param itemBuilder returns if two item builder has same durability
     */
    public boolean hasSameDurability(@NotNull ItemBuilder itemBuilder) {
        return getDurability() == itemBuilder.getDurability();
    }

    /**
     * @param itemBuilder returns if two item builder has same breakable stat
     */
    public boolean hasSameBreakableStat(@NotNull ItemBuilder itemBuilder) {
        return isUnbreakable() == itemBuilder.isUnbreakable();
    }

    /**
     * get type
     */
    public @NotNull Material getMaterial() {
        return itemStack.getType();
    }

    /**
     * get amount
     */
    public int getAmount() {
        return itemStack.getAmount();
    }

    /**
     * Set amount
     */
    public @NotNull ItemBuilder setAmount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    /**
     * get data
     */
    public int getData() {
        return Objects.requireNonNull(itemStack.getData()).getData();
    }

    /**
     * Set data
     */
    public @NotNull ItemBuilder setData(int data) {
        this.itemStack = new ItemStack(itemStack.getType(), itemStack.getAmount(), (byte) data);
        return this;
    }

    /**
     * get durability
     */
    public int getDurability() {
        return itemStack.getDurability();
    }

    /**
     * Set durability of item
     * /!\ 100 >= percent >= 0
     */
    public @NotNull ItemBuilder setDurability(float percent) {
        if (percent > 100.0) {
            return this;
        } else if (percent < 0.0) {
            return this;
        }
        itemStack.setDurability((short) (itemStack.getDurability() * (percent / 100)));
        return this;
    }

    /**
     * get item meta
     */
    public ItemMeta getItemMeta() {
        return itemMeta;
    }

    /**
     * get display name
     */
    public @NotNull String getDisplayName() {
        return itemStack.hasItemMeta() && itemMeta.hasDisplayName() ? itemMeta.getDisplayName() : "";
    }

    /**
     * get enchant
     */
    public @Nullable Map<Enchantment, Integer> getEnchantments() {
        return this.itemStack.hasItemMeta() && this.itemMeta.hasEnchants() ? this.itemMeta.getEnchants() : null;
    }

    /**
     * get lore
     */
    public @Nullable List<String> getLore() {
        return itemStack.hasItemMeta() && itemMeta.hasLore() ? itemMeta.getLore() : null;
    }

    /**
     * get item flag
     */
    public @Nullable Set<ItemFlag> getItemFlag() {
        return itemStack.hasItemMeta() && itemMeta.getItemFlags().size() > 0 ? itemMeta.getItemFlags() : null;
    }

    /**
     * get potion effects
     */
    public @Nullable List<PotionEffect> getPotionEffects() {
        return this.potionMeta != null && this.potionMeta.getCustomEffects().size() > 0 ? this.potionMeta.getCustomEffects() : null;
    }

    /**
     * get if item is or isn't unbreakable
     */
    public boolean isUnbreakable() {
        return itemStack.hasItemMeta() && itemMeta.isUnbreakable();
    }

    /**
     * @param unbreakable Set item in unbreakable/breakable
     */
    public @Nullable ItemBuilder setUnbreakable(boolean unbreakable) {
        if (this.itemMeta == null) {
            if (this.itemStack == null)
                return null;
            this.itemMeta = this.itemStack.getItemMeta();
        }
        this.itemMeta.setUnbreakable(unbreakable);
        this.itemStack.setItemMeta(this.itemMeta);
        return this;
    }

    public @NotNull ItemBuilder showArmorStandArms(boolean state) {
        ArmorStandMeta armorStandMeta = (ArmorStandMeta) itemStack.getItemMeta();
        armorStandMeta.setShowArms(state);
        this.itemStack.setItemMeta(armorStandMeta);
        return this;
    }

    public @NotNull ItemBuilder setArmorStandSmall(boolean state) {
        ArmorStandMeta armorStandMeta = (ArmorStandMeta) itemStack.getItemMeta();
        armorStandMeta.setSmall(state);
        this.itemStack.setItemMeta(armorStandMeta);
        return this;
    }

    /**
     * parse in json object without associate position
     */
    @SuppressWarnings("unchecked")
    public @NotNull JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("serialized", toString());
        return jsonObject;
    }

    /**
     * Convert ItemBuilder variable into a String
     */
    public @NotNull String toString() {
        String[] splitValues = new String[]{"{^}", "[^]", "`SECTION_TYPE`", "|", ","};
        StringBuilder itemBuilderString = new StringBuilder();
        itemBuilderString.append("item: ").append(splitValues[2]).append(splitValues[1])
                .append("type: ").append(getMaterial()).append(splitValues[1])
                .append("data: ").append(getData()).append(splitValues[1])
                .append("amount: ").append(getAmount()).append(splitValues[1])
                .append("durability: ").append(getDurability()).append(splitValues[1])
                .append("unbreakable: ").append(isUnbreakable()).append(splitValues[1])
                .append(splitValues[0]);
        itemBuilderString.append("other: ").append(splitValues[2]).append(splitValues[1]);
        itemBuilderString.append("position: ").append(getPosition()).append(splitValues[1]);
        itemBuilderString.append(splitValues[0]);
        if (this.itemStack.hasItemMeta()) {
            itemBuilderString.append("meta: ").append(splitValues[2]).append(splitValues[1]);
            if (getDisplayName() != null)
                itemBuilderString.append("name: ").append(getDisplayName()).append(splitValues[1]);
            if (getEnchantments() != null) {
                itemBuilderString.append("enchants: ");
                getEnchantments().forEach((enchantment, integer) -> itemBuilderString.append("enchantType: ")
                        .append(enchantment.getName()).append(splitValues[4])
                        .append("enchantLevel: ").append(integer)
                        .append(splitValues[4]).append(splitValues[3]));
                itemBuilderString.append(splitValues[1]);
            }
            if (getLore() != null) {
                itemBuilderString.append("lores: ");
                getLore().forEach(s -> itemBuilderString.append("lore: ").append(uncoloredString(s)).append(splitValues[3]));
                itemBuilderString.append(splitValues[1]);
            }
            if (getItemFlag() != null) {
                itemBuilderString.append("itemFlags: ");
                getItemFlag().forEach(s -> itemBuilderString.append("itemflag: ").append(s).append(splitValues[3]));
                itemBuilderString.append(splitValues[1]);
            }
            itemBuilderString.append(splitValues[0]);
        }

        return itemBuilderString.toString();
    }

    /**
     * Convert string variable into an ItemBuilder
     */
    public @NotNull ItemBuilder fromString(@NotNull String string) {
        String[] splitValues = new String[]{"\\{\\^}", "\\[\\^]", "`SECTION_TYPE`", "\\|", ","};
        ItemBuilder itemBuilder = new ItemBuilder();
        String[] sections = string.split(splitValues[0]);
        if (sections.length == 0 || Arrays.stream(sections).filter(s -> s.split(splitValues[2])[0]
                .equalsIgnoreCase("item: ")).count() != 1)
            return itemBuilder;
        String[] sectionType = sections[0].split(splitValues[2]);
        String[] object = sections[0].split(splitValues[1]);
        if (object.length < 6)
            return itemBuilder;
        itemSection(itemBuilder, sectionType, object);
        if (sections.length == 1 || !sections[1].startsWith("other: "))
            return itemBuilder;
        sectionType = sections[1].split(splitValues[2]);
        object = sections[1].split(splitValues[1]);
        otherPropertySection(itemBuilder, sectionType, object);
        if (sections.length == 2)
            return itemBuilder;
        sectionType = sections[2].split(splitValues[2]);
        object = sections[2].split(splitValues[1]);
        if (sectionType[0].equalsIgnoreCase("meta: "))
            metaSection(itemBuilder, sectionType, object);
        return itemBuilder;
    }

    /**
     * This method returns the item
     */
    private void itemSection(@NotNull ItemBuilder itemBuilder, String[] sectionType, String[] object) {
        Arrays.asList(object).forEach(s -> {
            if (!s.equalsIgnoreCase(sectionType[0])) {
                if (s.startsWith("type: "))
                    itemBuilder.setItem(Material.valueOf(s.replace("type: ", "")));
                if (s.startsWith("data: "))
                    itemBuilder.setData(Integer.parseInt(s.replace("data: ", "")));
                if (s.startsWith("amount: "))
                    itemBuilder.setAmount(Integer.parseInt(s.replace("amount: ", "")));
                if (s.startsWith("durability: "))
                    itemBuilder.setNewDurability(Integer.parseInt(s.replace("durability: ", "")));
                if (s.startsWith("unbreakable: "))
                    itemBuilder.setUnbreakable(Boolean.parseBoolean(s.replace("unbreakable: ", "")));
            }
        });
    }

    /**
     * This method returns specific properties of item from of ItemBuilder
     */
    private void otherPropertySection(@NotNull ItemBuilder itemBuilder, String[] sectionType, String[] object) {
        Arrays.asList(object).forEach(s -> {
            if (!s.equalsIgnoreCase(sectionType[0])) {
                if (s.startsWith("position: ")) {
                    itemBuilder.setPosition(Integer.parseInt(s.replace("position: ", "")));
                }
            }
        });
    }

    /**
     * This method returns the meta of the item
     */
    private void metaSection(@NotNull ItemBuilder itemBuilder, String[] sectionType, String[] object) {
        String[] splitValues = new String[]{"\\{\\^}", "\\[\\^]", "`SECTION_TYPE`", "\\|", ","};
        Arrays.asList(object).forEach(s -> {
            if (!s.equalsIgnoreCase(sectionType[0])) {
                if (s.startsWith("name: "))
                    itemBuilder.setName(coloredString(s.replace("name: ", "")));
                if (s.startsWith("enchants: ")) {
                    Map<Enchantment, Integer> enchantmentMap = new HashMap<>();
                    String[] enchant = s.split(splitValues[3]);
                    Arrays.asList(enchant).forEach(s1 -> {
                        String[] enchantObject = s1.split(splitValues[4]);
                        enchantmentMap.put(Enchantment.getByName(enchantObject[0].replace("enchants: ", "")
                                        .replace("enchantType: ", "")),
                                Integer.valueOf(enchantObject[1].replace("enchantLevel: ", "")));
                    });
                    itemBuilder.setEnchants(enchantmentMap);
                }
                if (s.startsWith("lores: ")) {
                    String[] lores = s.split(splitValues[3]);
                    List<String> loreList = new ArrayList<>();
                    Arrays.asList(lores).forEach(s1 -> loreList.add(coloredString(s1)
                            .replace("lores: ", "").replace("lore: ", "")));
                    itemBuilder.addLore(loreList);
                }
                if (s.startsWith("itemFlags: ")) {
                    String[] itemFlags = s.split(splitValues[3]);
                    List<ItemFlag> itemFlagList = new ArrayList<>();
                    Arrays.asList(itemFlags).forEach(s1 -> itemFlagList.add(ItemFlag.valueOf(s1.replace("itemFlags: ", "")
                            .replace("itemflag: ", ""))));
                    itemBuilder.addItemFlag(itemFlagList);
                }
            }
        });
    }

    /**
     * Replace § to &
     */
    private @NotNull String uncoloredString(@NotNull String string) {
        return string.replace("§", "&");
    }

    /**
     * Replace & to §
     */
    private @NotNull String coloredString(@NotNull String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

}