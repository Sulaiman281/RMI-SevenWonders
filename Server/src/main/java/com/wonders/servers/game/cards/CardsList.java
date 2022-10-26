package com.wonders.servers.game.cards;

public class CardsList {

    public static String[] age_I = {
            // BROWN CARDS 10
            "BC_LUMBER YARD_WOOD:1_FREE",
            "BC_STONE PIT_STONE:1_FREE",
            "BC_CLAY POOL_CLAY:1_FREE",
            "BC_ORE VEIN_ORE:1_FREE",
            "BC_TREE FARM_WOOD:1/CLAY:1_COIN:1",
            "BC_EXCAVATION_STONE:1/CLAY:1_COIN:1",
            "BC_CLAY PIT_CLAY:1/ORE:1_COIN:1",
            "BC_TIMBER YARD_STONE:1/WOOD:1_COIN:1",
            "BC_FOREST CAVE_WOOD:1/ORE:1_COIN:1",
            "BC_MINE_ORE:1/STONE:1_COIN:1",

            // GREY CARDS 3
            "GC_LOOM_CLOTHE:1_FREE",
            "GC_GLASSWORKS_GLASS:1_FREE",
            "GC_PRESS_PAPYRUS:1_FREE",

            //BLUE CARDS 4
            "HC_PAWNSHOP_VP:3_FREE",
            "HC_BATHS_VP:3_STONE:1",
            "HC_ALTAR_VP:2_FREE",
            "HC_THEATER_VP:2_FREE",

            //RED CARDS 3
            "RC_STOCKADE_SHIELD:1_WOOD:1",
            "RC_BARRACKS_SHIELD:1_ORE:1",
            "RC_GUARD TOWER_SHIELD:1_CLAY:1",

            //YELLOW CARDS 4 ( TYPE_NAME_TargetPlayer-TargetCard=PRIZE_COST )
            "YC_TAVERN_M-NONE=COIN:5_FREE",
            "YC_EAST TRADING POST_RN-BC=COIN:1_FREE",
            "YC_WEST TRADING POST_LN-BC=COIN:1_FREE",
            "YC_MARKETPLACE_BN-GC=COIN:1_FREE",

            //GREEN CARDS 3
            "$C_APOTHECARY_COMPASS:1_CLOTHE:1",
            "$C_WORKSHOP_GEAR:1_GLASS:1",
            "$C_SCRIPTORIUM_TABLET:1_PAPYRUS:1"
    };

    public static String[] age_II = {
            //BROWN CARDS 4
            "BC_SAWMILL_WOOD:2_COIN:1",
            "BC_QUARRY_STONE:2_COIN:1",
            "BC_BRICKYARD_CLAY:2_COIN:1",
            "BC_FOUNDRY_ORE:2_COIN:1",

            //GREY CARDS 3
            "GC_LOOM_CLOTHE:1_FREE",
            "GC_GLASSWORKS_GLASS:1_FREE",
            "GC_PRESS_PAPYRUS:1_FREE",

            //BLUE CARDS 4
            "HC_AQUEDUCT_VP:5_STONE:3",
            "HC_TEMPLE_VP:3_WOOD:1&CLAY:1&GLASS:1",
            "HC_STATUE_VP:4_WOOD:1&ORE:2",
            "HC_COURTHOUSE_VP:4_CLAY:2&CLOTHE:1",

            //RED CARDS 4
            "RC_WALLS_SHIELD:2_STONE:3",
            "RC_TRAINNG GROUND_SHIELD:2_WOOD:1&ORE:2",
            "RC_STABLES_SHIELD:2_ORE:1&CLAY:1&WOOD:1",
            "RC_ARCHERY RANGE_SHIELD:2_WOOD:2&ORE:1",

            //YELLOW CARDS 4 ( TYPE_NAME_TargetPlayer-TargetCard=PRIZE_COST )
            "YC_FORUM_M-NONE=CLOTHE:1/GLASS:1/PAPYRUS:1_CLAY:2",
            "YC_CARAVANSERY_M-NONE=CLAY:1/STONE:1/ORE:1/WOOD:1_WOOD:2",
            "YC_VINEYARD_ABN-BC=COIN:1_FREE",
            "YC_BAZAR_ABN-GC=COIN:2_FREE",

            //GREEN CARDS  4
            "$C_DISPENSARY_COMPASS:1_ORE:2&GLASS:1",
            "$C_LABORATORY_GEAR:1_CLAY:2&PAPYRUS:1",
            "$C_LIBRARY_TABLET:1_STONE:2&CLOTHE:1",
            "$C_SCHOOL_TABLET:1_WOOD:1&PAPYRUS:1"
    };

    public static String[] age_III = {
            //PURPLE CARDS 10  ( TYPE_NAME_TargetPlayer-TargetCard=PRIZE_COST )
            "PC_WORKERS GUILD_BN-BC=VP:1_ORE:2&CLAY:1&STONE:1&WOOD:1",
            "PC_CRAFTMENS GUILD_BN-GC=VP:2_ORE:2&STONE:2",
            "PC_TRADERS GUILD_BN-YC=VP:1_CLOTHE:1&PAPYRUS:1_GLASS:1",
            "PC_PHILOSOPHERS GUILD_BN-$C=VP:1_CLAY:3&CLOTHE:1&PAPYRUS:1",
            "PC_SPIES GUILD_BN-RC=VP:1_CLAY:3&GLASS:1",
            "PC_STRATEGISTS GUILD_BN-CP=VP:1_ORE:2&STONE:1&CLOTHE:1", // conflicts of armies point
            "PC_SHIPOWNERS GUILD_M-BC&GC&PC=VP:1_WOOD:3&PAPYRUS:1&GLASS:1",
            "PC_SCIENTISTS GUILD_M-NONE=COMPASS:1/GEAR:1/TABLET:1_WOOD:2&ORE:2&PAPYRUS:1",
            "PC_MAGISTRATES GUILD_BN-HC=VP:1_WOOD:3&STONE:1&CLOTHE:1",
            "PC_BUILDERS GUILD_ABN-STAGE=VP:1_STONE:2&CLAY:2&GLASS:1",

            //BLUE CARDS 4
            "HC_PANTHEON_VP:7_CLAY:2&ORE:1&PAPYRUS:1&CLOTHE:1&GLASS:1",
            "HC_GARDENS_VP:5_WOOD:1&CLAY:2",
            "HC_TOWN HALL_VP:6_GLASS:1&ORE:1&STONE:2",
            "HC_PALACE_VP:8_GLASS:1&PAPYRUS:1&CLOTHE:1&CLAY:1&WOOD:1&ORE:1&STONE:1",

            //RED CARDS 4
            "RC_FORTIFICATIONS_SHIELD:3_STONE:1&ORE:3",
            "RC_CIRCUS_SHIELD:3_STONE:3&ORE:1",
            "RC_ARSENAL_SHIELD:3_ORE:1&WOOD:2&CLOTHE:1",
            "RC_SIEGE WORKSHOP_SHIELD:3_WOOD:1&CLAY:3",

            //YELLOW CARDS 4 ( TYPE_NAME_TargetPlayer-TargetCard=PRIZE_COST )
            "YC_HAVEN_M-BC=VP:1&COIN:1_CLOTHE:1&ORE:1&WOOD:1",
            "YC_LIGHTHOUSE_M-YC=VP:1&COIN:1_GLASS:1&STONE:1",
            "YC_CHAMBER OF COMMERCE_M-GC=VP:2&COIN:2_CLAY:2&PAPYRUS:1",
            "YC_ARENA_M-STAGE=VP:1&COIN:3_ORE:1&STONE:2",

            //GREEN CARD  5
            "$C_LODGE_COMPASS:1_CLAY:2&CLOTHE:1&PAPYRUS:1",
            "$C_OBSERVATORY_GEAR:1_ORE:2&GLASS:1&CLOTHE:1",
            "$C_UNIVERSITY_TABLET:1_WOOD:2&PAPYRUS:1&GLASS:1",
            "$C_ACADEMY_COMPASS:1_STONE:3&GLASS:1",
            "$C_STUDY_GEAR:1_WOOD:1&PAPYRUS:1&CLOTHE:1"
    };
}
