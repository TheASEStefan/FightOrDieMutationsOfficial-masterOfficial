package net.teamabyssalofficial.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.google.common.collect.Lists;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.util.List;

public class FightOrDieMutationsConfig {
    public static int globalVariable = 0;
    public static final Server SERVER;
    public static final ForgeConfigSpec SERVER_SPEC;
    public static final DataGen DATAGEN;
    public static final ForgeConfigSpec DATAGEN_SPEC;


    static {

        Pair<Server, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Server::new);
        SERVER = commonSpecPair.getLeft();
        SERVER_SPEC = commonSpecPair.getRight();

        Pair<DataGen , ForgeConfigSpec> commonPair = new ForgeConfigSpec.Builder().configure(DataGen::new);
        DATAGEN = commonPair.getLeft();
        DATAGEN_SPEC = commonPair.getRight();






    }
    public static class Server {

        public final ForgeConfigSpec.ConfigValue<List<? extends String>> blacklist;
        public final ForgeConfigSpec.ConfigValue<Integer> mob_cap;
        public final ForgeConfigSpec.ConfigValue<Integer> mobSpawnAttempts;
        public final ForgeConfigSpec.ConfigValue<Integer> ticksBeforeHordeSpawning;
        public final ForgeConfigSpec.ConfigValue<Integer> additionalRandomizedTicks;

        public final ForgeConfigSpec.ConfigValue<List<? extends String>> hive_sickness;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> dimension_parameters;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> spawns;
        public final ForgeConfigSpec.ConfigValue<Double> shiller_health;
        public final ForgeConfigSpec.ConfigValue<Double> springer_health;
        public final ForgeConfigSpec.ConfigValue<Double> springer_damage;
        public final ForgeConfigSpec.ConfigValue<Double> mutated_human_health;
        public final ForgeConfigSpec.ConfigValue<Double> mutated_human_damage;
        public final ForgeConfigSpec.ConfigValue<Double> mutated_player_health;
        public final ForgeConfigSpec.ConfigValue<Double> mutated_player_damage;
        public final ForgeConfigSpec.ConfigValue<Boolean> mutated_player_breaks_blocks;
        public final ForgeConfigSpec.ConfigValue<Double> mutated_human_head_health;
        public final ForgeConfigSpec.ConfigValue<Double> mutated_human_head_damage;
        public final ForgeConfigSpec.ConfigValue<Double> mutated_villager_health;
        public final ForgeConfigSpec.ConfigValue<Double> mutated_villager_damage;
        public final ForgeConfigSpec.ConfigValue<Double> mutated_cow_head_health;
        public final ForgeConfigSpec.ConfigValue<Double> mutated_cow_head_damage;
        public final ForgeConfigSpec.ConfigValue<Double> mutated_villager_head_health;
        public final ForgeConfigSpec.ConfigValue<Double> mutated_villager_head_damage;
        public final ForgeConfigSpec.ConfigValue<Double> mutated_player_head_health;
        public final ForgeConfigSpec.ConfigValue<Double> mutated_player_head_damage;
        public final ForgeConfigSpec.ConfigValue<Double> mutated_enderman_head_health;
        public final ForgeConfigSpec.ConfigValue<Double> mutated_enderman_head_damage;
        public final ForgeConfigSpec.ConfigValue<Boolean> mutated_enderman_head_teleportation;
        public final ForgeConfigSpec.ConfigValue<Boolean> mutated_enderman_head_sensible_to_water;
        public final ForgeConfigSpec.ConfigValue<Double> mutated_creeper_head_health;
        public final ForgeConfigSpec.ConfigValue<Double> mutated_creeper_head_damage;
        public final ForgeConfigSpec.ConfigValue<Boolean> mutated_creeper_head_explosion;
        public final ForgeConfigSpec.ConfigValue<Double> mutated_cow_health;
        public final ForgeConfigSpec.ConfigValue<Double> mutated_cow_damage;
        public final ForgeConfigSpec.ConfigValue<Double> mutated_sheep_health;
        public final ForgeConfigSpec.ConfigValue<Double> mutated_sheep_damage;
        public final ForgeConfigSpec.ConfigValue<Double> mutated_pig_health;
        public final ForgeConfigSpec.ConfigValue<Double> mutated_pig_damage;
        public final ForgeConfigSpec.ConfigValue<Double> mutated_fox_health;
        public final ForgeConfigSpec.ConfigValue<Double> mutated_fox_damage;
        public final ForgeConfigSpec.ConfigValue<Double> mutated_creeper_health;
        public final ForgeConfigSpec.ConfigValue<Double> mutated_creeper_damage;
        public final ForgeConfigSpec.ConfigValue<Double> mutated_creeper_explosion_radius;
        public final ForgeConfigSpec.ConfigValue<Double> mutated_enderman_health;
        public final ForgeConfigSpec.ConfigValue<Double> mutated_enderman_damage;
        public final ForgeConfigSpec.ConfigValue<Boolean> mutated_enderman_reinforcements;
        public final ForgeConfigSpec.ConfigValue<Boolean> mutated_enderman_teleportation;
        public final ForgeConfigSpec.ConfigValue<Boolean> mutated_enderman_sensible_to_water;
        public final ForgeConfigSpec.ConfigValue<Double> mutated_enderman_reinforcement_rate;
        public final ForgeConfigSpec.ConfigValue<Boolean> mutated_human_mutation;
        public final ForgeConfigSpec.ConfigValue<Boolean> mutated_villager_mutation;
        public final ForgeConfigSpec.ConfigValue<Boolean> mutated_player_mutation;
        public final ForgeConfigSpec.ConfigValue<Boolean> mutated_enderman_mutation;
        public final ForgeConfigSpec.ConfigValue<Boolean> mutated_cow_mutation;
        public final ForgeConfigSpec.ConfigValue<Boolean> mutated_sheep_mutation;
        public final ForgeConfigSpec.ConfigValue<Boolean> mutated_pig_mutation;
        public final ForgeConfigSpec.ConfigValue<Boolean> mutated_fox_mutation;
        public final ForgeConfigSpec.ConfigValue<Boolean> mutated_creeper_mutation;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> mutated_human_mutations;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> mutated_villager_mutations;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> mutated_cow_mutations;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> mutated_sheep_mutations;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> mutated_pig_mutations;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> mutated_fox_mutations;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> mutated_enderman_mutations;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> mutated_creeper_mutations;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> springer_targeted_infection_entities;
        public final ForgeConfigSpec.ConfigValue<Boolean> random_disturbing_sounds;
        public final ForgeConfigSpec.ConfigValue<Boolean> stomach_growl_detection;
        public final ForgeConfigSpec.ConfigValue<Boolean> enable_horde;

        public Server(ForgeConfigSpec.Builder builder) {
            builder.push("Shiller");
            this.shiller_health = builder.comment("Default 6").defineInRange("Sets Shiller's Max health", 6, 2, Double.MAX_VALUE);
            builder.pop();
            builder.push("springer");
            this.springer_health = builder.comment("Default 12").defineInRange("Sets springer's Max health", 12, 5, Double.MAX_VALUE);
            this.springer_damage = builder.comment("Default 5").defineInRange("Sets springer's Damage", 5, 2, Double.MAX_VALUE);
            builder.pop();
            builder.push("mutated Human");
            this.mutated_human_health = builder.comment("Default 20").defineInRange("Sets mutated Human's Max health", 20, 10, Double.MAX_VALUE);
            this.mutated_human_damage = builder.comment("Default 8").defineInRange("Sets mutated Human's Damage", 8, 4, Double.MAX_VALUE);
            builder.pop();
            builder.push("mutated player");
            this.mutated_player_health = builder.comment("Default 20").defineInRange("Sets mutated player's Max health", 20, 10, Double.MAX_VALUE);
            this.mutated_player_damage = builder.comment("Default 10").defineInRange("Sets mutated player's Damage", 10, 5, Double.MAX_VALUE);
            this.mutated_player_breaks_blocks = builder.comment("Default true").define("Should the mutated player break blocks?",true);
            builder.pop();
            builder.push("mutated Villager");
            this.mutated_villager_health = builder.comment("Default 22").defineInRange("Sets mutated Villager's Max health", 22, 12, Double.MAX_VALUE);
            this.mutated_villager_damage = builder.comment("Default 9").defineInRange("Sets mutated Villager's Damage", 9, 4, Double.MAX_VALUE);
            builder.pop();
            builder.push("mutated Cow Head");
            this.mutated_cow_head_health = builder.comment("Default 6").defineInRange("Sets mutated Cow Head's Max health", 6, 3, Double.MAX_VALUE);
            this.mutated_cow_head_damage = builder.comment("Default 3").defineInRange("Sets mutated Cow Head's Damage", 3, 1, Double.MAX_VALUE);
            builder.pop();
            builder.push("mutated Human Head");
            this.mutated_human_head_health = builder.comment("Default 7").defineInRange("Sets mutated Human Head's Max health", 7, 4, Double.MAX_VALUE);
            this.mutated_human_head_damage = builder.comment("Default 3").defineInRange("Sets mutated Human Head's Damage", 3, 1, Double.MAX_VALUE);
            builder.pop();
            builder.push("mutated Villager Head");
            this.mutated_villager_head_health = builder.comment("Default 8").defineInRange("Sets mutated Villager Head's Max health", 8, 4, Double.MAX_VALUE);
            this.mutated_villager_head_damage = builder.comment("Default 3").defineInRange("Sets mutated Villager Head's Damage", 3, 1, Double.MAX_VALUE);
            builder.pop();
            builder.push("mutated player Head");
            this.mutated_player_head_health = builder.comment("Default 8").defineInRange("Sets mutated player Head's Max health", 8, 4, Double.MAX_VALUE);
            this.mutated_player_head_damage = builder.comment("Default 3").defineInRange("Sets mutated player Head's Damage", 3, 1, Double.MAX_VALUE);
            builder.pop();
            builder.push("mutated Creeper Head");
            this.mutated_creeper_head_health = builder.comment("Default 9").defineInRange("Sets mutated Creeper Head's Max health", 9, 4, Double.MAX_VALUE);
            this.mutated_creeper_head_damage = builder.comment("Default 4").defineInRange("Sets mutated Creeper Head's Damage", 4, 2, Double.MAX_VALUE);
            this.mutated_creeper_head_explosion = builder.comment("Default true").define("Should mutated Creeper Heads explode when in contact when close to it's target?",true);
            builder.pop();
            builder.push("mutated Enderman Head");
            this.mutated_enderman_head_health = builder.comment("Default 13").defineInRange("Sets mutated Enderman Head's Max health", 13, 7, Double.MAX_VALUE);
            this.mutated_enderman_head_damage = builder.comment("Default 5").defineInRange("Sets mutated Enderman Head's Damage", 5, 2, Double.MAX_VALUE);
            this.mutated_enderman_head_teleportation = builder.comment("Default true").define("Should mutated Enderman Heads use teleportation abilities?",true);
            this.mutated_enderman_head_sensible_to_water = builder.comment("Default true").define("Should mutated Enderman Heads take damage when in contact with water?",true);
            builder.pop();
            builder.push("mutated Cow");
            this.mutated_cow_health = builder.comment("Default 16").defineInRange("Sets mutated Cow's Max health", 16, 7, Double.MAX_VALUE);
            this.mutated_cow_damage = builder.comment("Default 8").defineInRange("Sets mutated Cow's Damage", 8, 3, Double.MAX_VALUE);
            builder.pop();
            builder.push("mutated Sheep");
            this.mutated_sheep_health = builder.comment("Default 14").defineInRange("Sets mutated Sheep's Max health", 14, 4, Double.MAX_VALUE);
            this.mutated_sheep_damage = builder.comment("Default 7").defineInRange("Sets mutated Sheep's Damage", 7, 3, Double.MAX_VALUE);
            builder.pop();
            builder.push("mutated Pig");
            this.mutated_pig_health = builder.comment("Default 12").defineInRange("Sets mutated Pig's Max health", 12, 3, Double.MAX_VALUE);
            this.mutated_pig_damage = builder.comment("Default 7").defineInRange("Sets mutated Pig's Damage", 7, 2, Double.MAX_VALUE);
            builder.pop();
            builder.push("mutated Fox");
            this.mutated_fox_health = builder.comment("Default 10").defineInRange("Sets mutated Fox's Max health", 10, 3, Double.MAX_VALUE);
            this.mutated_fox_damage = builder.comment("Default 7").defineInRange("Sets mutated Fox's Damage", 7, 2, Double.MAX_VALUE);
            builder.pop();
            builder.push("mutated Creeper");
            this.mutated_creeper_health = builder.comment("Default 12").defineInRange("Sets mutated Creeper's Max health", 12, 5, Double.MAX_VALUE);
            this.mutated_creeper_damage = builder.comment("Default 5").defineInRange("Sets mutated Creeper's Damage", 5, 2, Double.MAX_VALUE);
            this.mutated_creeper_explosion_radius = builder.comment("Default 2.15").defineInRange("Sets mutated Creeper's Explosion Radius", 2.15, 1.1, Double.MAX_VALUE);
            builder.pop();
            builder.push("mutated Enderman");
            this.mutated_enderman_health = builder.comment("Default 55").defineInRange("Sets mutated Enderman's Max health", 55, 20, Double.MAX_VALUE);
            this.mutated_enderman_damage = builder.comment("Default 12").defineInRange("Sets mutated Enderman's Damage", 12, 5, Double.MAX_VALUE);
            this.mutated_enderman_reinforcement_rate = builder.comment("Default 0.95").defineInRange("Sets the chance of an enderman to bring reinforcements, the lower the number, the smaller the chance", 0.95, 0.20, Double.MAX_VALUE);
            this.mutated_enderman_reinforcements = builder.comment("Default true").define("Should mutated Endermans bring reinforcements with teleportation?",true);
            this.mutated_enderman_teleportation = builder.comment("Default true").define("Should mutated Endermans use teleportation abilities?",true);
            this.mutated_enderman_sensible_to_water = builder.comment("Default true").define("Should mutated Endermans take damage when in contact with water?",true);
            builder.pop();

            builder.push("Spawns");
            this.mob_cap = builder.comment("Default 60").define("MobCap",60);
            this.dimension_parameters = builder.comment("Default minecraft:is_overworld").defineList("Dictates in what biome the parasites spawn",
                    Lists.newArrayList("minecraft:is_overworld") , o -> o instanceof String);
            this.spawns = builder.defineList("mob|weight|minimum|maximum",
                    Lists.newArrayList("fight_or_die:shiller|45|1|3", "fight_or_die:springer|30|1|2", "fight_or_die:mutated_human|28|1|2", "fight_or_die:mutated_player|20|1|2", "fight_or_die:mutated_villager|25|1|2", "fight_or_die:mutated_cow|25|1|2", "fight_or_die:mutated_sheep|25|1|2", "fight_or_die:mutated_pig|25|1|2", "fight_or_die:mutated_fox|22|1|2", "fight_or_die:mutated_creeper|9|1|1", "fight_or_die:mutated_enderman|3|1|1") , o -> o instanceof String);
            builder.pop();

            builder.push("Targeting Tasks");

            this.blacklist = builder.defineList("Mobs Not Targeted",
                    Lists.newArrayList(
                            "minecraft:squid", "minecraft:bat", "minecraft:armor_stand", "minecraft:creeper", "minecraft:ghast", "minecraft:falling_block", "minecraft:abstract_arrow", "minecraft:arrow", "minecraft:spectral_arrow", "minecraft:trident") , o -> o instanceof String);

            builder.pop();
            builder.push("Horde");
            this.enable_horde = builder.comment("Default true").define("Should mutation hordes spawn?",true);
            this.mobSpawnAttempts = builder.comment("Default 30").define("Approximate Number of Mobs Spawning",30);
            this.ticksBeforeHordeSpawning= builder.comment("Default 36000").define("Ticks before the horde spawning (not including the random ticks based on chance which add do the horde spawning time)",36000);
            this.additionalRandomizedTicks = builder.comment("Default 1200").define("Additional horde ticks",1200);
            builder.pop();


            builder.push("Effects");
            this.hive_sickness = builder.defineList("Mobs that are immune to the hive sickness infection",
                    Lists.newArrayList(
                            "minecraft:ghast"
                            , "minecraft:magma_cube"
                            , "minecraft:phantom"
                            , "minecraft:snow_golem"
                            , "minecraft:stray"
                            , "minecraft:skeleton" ) , o -> o instanceof String);
            builder.pop();
            builder.push("Mutations");
            this.mutated_human_mutation = builder.comment("Default true").define("Should zombies convert into their mutated counterpart?",true);
            this.mutated_cow_mutation = builder.comment("Default true").define("Should cows convert into their mutated counterpart?",true);
            this.mutated_sheep_mutation = builder.comment("Default true").define("Should sheeps convert into their mutated counterpart?",true);
            this.mutated_pig_mutation = builder.comment("Default true").define("Should pigs convert into their mutated counterpart?",true);
            this.mutated_fox_mutation = builder.comment("Default true").define("Should foxes convert into their mutated counterpart?",true);
            this.mutated_villager_mutation = builder.comment("Default true").define("Should villagers convert into their mutated counterpart?",true);
            this.mutated_player_mutation = builder.comment("Default true").define("Should players convert into their mutated counterpart?",true);
            this.mutated_creeper_mutation = builder.comment("Default true").define("Should creepers convert into their mutated counterpart?",true);
            this.mutated_enderman_mutation = builder.comment("Default true").define("Should endermans convert into their mutated counterpart?",true);

            this.springer_targeted_infection_entities = builder.defineList("Mobs that the springer will try to infect (Animals only)",
                    Lists.newArrayList(
                            "minecraft:sheep", "minecraft:cow", "minecraft:fox", "minecraft:pig") , o -> o instanceof String);

            this.mutated_human_mutations = builder.defineList("Mobs that can turn into Mutated Humans",
                    Lists.newArrayList(
                            "minecraft:zombie") , o -> o instanceof String);

            this.mutated_villager_mutations = builder.defineList("Mobs that can turn into Mutated Villagers",
                    Lists.newArrayList(
                            "minecraft:villager", "rotted:farmer", "guardvillagers:guard") , o -> o instanceof String);

            this.mutated_cow_mutations = builder.defineList("Mobs that can turn into Mutated Cows",
                    Lists.newArrayList(
                            "minecraft:cow") , o -> o instanceof String);

            this.mutated_sheep_mutations = builder.defineList("Mobs that can turn into Mutated Sheeps",
                    Lists.newArrayList(
                            "minecraft:sheep") , o -> o instanceof String);

            this.mutated_pig_mutations = builder.defineList("Mobs that can turn into Mutated Pigs",
                    Lists.newArrayList(
                            "minecraft:pig") , o -> o instanceof String);

            this.mutated_fox_mutations = builder.defineList("Mobs that can turn into Mutated Foxes",
                    Lists.newArrayList(
                            "minecraft:cow") , o -> o instanceof String);

            this.mutated_enderman_mutations = builder.defineList("Mobs that can turn into Mutated Endermans",
                    Lists.newArrayList(
                            "minecraft:enderman") , o -> o instanceof String);

            this.mutated_creeper_mutations = builder.defineList("Mobs that can turn into Mutated Creepers",
                    Lists.newArrayList(
                            "minecraft:creeper") , o -> o instanceof String);


            builder.pop();
            builder.push("Ambience");
            this.random_disturbing_sounds = builder.comment("Default true").define("Should players hear disturbing sounds from time to time? (Note: this affects only one random player from the server)",true);
            builder.pop();
            builder.push("Hints for Infected Animals");
            this.stomach_growl_detection = builder.comment("Default true").define("Should certain animals that have mutation forms make stomach growl sounds from time to time if infected with the sickness?",true);
            builder.pop();


        }
    }
    public static class DataGen {
        public final ForgeConfigSpec.ConfigValue<Integer> phase1_points;
        public final ForgeConfigSpec.ConfigValue<Integer> phase2_points;
        public final ForgeConfigSpec.ConfigValue<Integer> phase3_points;
        public final ForgeConfigSpec.ConfigValue<Integer> phase4_points;
        public final ForgeConfigSpec.ConfigValue<Integer> phase5_points;
        public final ForgeConfigSpec.ConfigValue<Integer> devices_points;

        public final ForgeConfigSpec.ConfigValue<List<? extends String>> name;

        public final ForgeConfigSpec.ConfigValue<List<? extends String>> mutated_player_items;
        public DataGen(ForgeConfigSpec.Builder builder) {
            builder.push("Phases");
            this.phase1_points = builder.comment("Default 1000").define("Sets points required to enter phase 1",1000);
            this.phase2_points = builder.comment("Default 10000").define("Sets points required to enter phase 2",10000);
            this.phase3_points = builder.comment("Default 50000").define("Sets points required to enter phase 3",50000);
            this.phase4_points = builder.comment("Default 500000").define("Sets points required to enter phase 4",500000);
            this.phase5_points = builder.comment("Default 2000000").define("Sets points required to enter phase 5",2000000);
            builder.pop();
            builder.push("Main Hand Items for the Mutated Player");
            this.mutated_player_items = builder.defineList("Main Hand Slot for the Mutated Player",
                    Lists.newArrayList("minecraft:stone_sword|20" , "minecraft:stone_axe|20", "minecraft:iron_axe|15", "minecraft:iron_pickaxe|35", "minecraft:stone_pickaxe|25" , "minecraft:iron_sword|25") , o -> o instanceof String);
            builder.pop();
            builder.push("Device Points");
            this.devices_points = builder.comment("Default 1000").define("Sets points that will be added / subtracted by the devices",1000);
            builder.pop();
            builder.push("player Names");
            this.name = builder.defineList("mutated player Possible Names",
                    Lists.newArrayList(
                            "ASEStefan", "Nightfox", "Kronoz", "DAKOTA", "JC", "Korben", "Beta",
                    "Renovated", "TeamAbyssal", "XEliteXCraftersX", "TqLxQuanZ", "Ian", "Andy", "Spaghetti",
                            "AnnoyingSrpFan123", "YOASOBI", "Quattro", "NotMilkyCat", "Daralexen", "Chickon98",
                            "ChingChilly", "wRatte", "ivan", "PHO3N1X", "TaiwanIsTheTrueChina", "kevin",
                            "WinVic", "Wikipedia", "Mr.Lambert", "Dr.Pilot", "Harbinger", "LukeUCraft",
                            "purpleskittle", "Adrian", "Isha21", "WitherBean", "Dr.Korpus", "Dr.Simon", "Dr.Robert",
                            "MarioThePlumber", "Lelouch VI Britannia", "Light Yagami", "Mutationcraft", "yorkmousemodz",
                            "Mattias", "entiral", "Cock Jamers", "Chad", "DopeMan", "FlipperIvy") , o -> o instanceof String);
            builder.pop();
        }

    }

    public static void loadConfig(ForgeConfigSpec config, String path) {
        final CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().autosave()
                .writingMode(WritingMode.REPLACE).build();
        file.load();
        config.setConfig(file);
    }

}