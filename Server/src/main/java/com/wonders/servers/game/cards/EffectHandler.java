package com.wonders.servers.game.cards;

import com.wonders.servers.game.PlayerMatchState;
import com.wonders.servers.game.cities.City;

public class EffectHandler {

    public static void add_card_in_city(Card card, PlayerMatchState pms, PlayerMatchState ln, PlayerMatchState rn) {
        switch (card.getType()) {
            case "HC": {
                int amount = Integer.parseInt(card.getEffect().split(":")[1]);
                pms.addBlue_cardPoints(amount);
                break;
            }
            case "RC": {
                String[] s = card.getEffect().split(":");
                pms.addPoints(Integer.parseInt(s[1]), s[0]);
                break;
            }
            case "YC":
            case "PC": {
                switch (card.getTarget_players()) {
                    case "BN" -> {
                        if (card.getTarget_cards().equals("CP")) {
                            int l = ln.getConflicts_points();
                            int r = ln.getConflicts_points();
                            int amount = Integer.parseInt(card.getPrize().split(":")[1]);
                            pms.addPoints((l * r) * amount, card.getType());
                        } else {
                            int l = ln.getCity().calculate_type_cards(card.getTarget_cards());
                            int r = rn.getCity().calculate_type_cards(card.getTarget_cards());
                            int amount = Integer.parseInt(card.getPrize().split(":")[1]);
                            pms.addPoints((l + r) * amount, card.getType());
                        }
                    }
                    case "M" -> {
                        if (card.getTarget_cards().equals("STAGE")) {
                            int amount = Integer.parseInt(card.getPrize().split(":")[1]);
                            int m = pms.getCity().completedStages();
                            pms.addPoints(m * amount, card.getType());
                        } else if (card.getTarget_cards().contains("&")) {
                            String[] cards = card.getTarget_cards().split("&");
                            int amount = Integer.parseInt(card.getPrize().split(":")[1]);
                            for (String s : cards) {
                                int m = pms.getCity().calculate_type_cards(s);
                                pms.addPoints(m * amount, card.getType());
                            }
                        } else if (card.getTarget_cards().equals("NONE")) {
                            if (card.getPrize().contains("COIN")) {
                                pms.add_balance(Integer.parseInt(card.getPrize().split(":")[1]));
                            } else if (card.getPrize().contains("/")) {
                                extractResources(card.getPrize(), pms.getCity());
                            } else if (card.getTarget_cards().equals("BC")) {
                                if (card.getTarget_players().equals("RN")) {
                                    pms.getCity().setBrownDiscount(2);
                                } else if (card.getTarget_players().equals("LN")) {
                                    pms.getCity().setBrownDiscount(1);
                                }
                            } else if (card.getTarget_cards().equals("GC")) {
                                if (card.getTarget_players().equals("BN")) {
                                    pms.getCity().setGreyDiscount(3);
                                }
                            } else if (card.getTarget_players().equals("ABN")) {

                            }
                        } else {
                            if (card.getPrize().contains("&")) {
                                String[] prizes = card.getPrize().split("&");
                                int cards = pms.getCity().calculate_type_cards(card.getTarget_cards().split(":")[0]);
                                for (String prize : prizes) {
                                    String[] p = prize.split(":");
                                    int amount = Integer.parseInt(p[1]);
                                    pms.addPoints(amount * cards, p[0]);
                                }
                            }
                        }
                    }
                    case "ABN" -> {
                        int amount = Integer.parseInt(card.getPrize().split(":")[1]);
                        if (card.getTarget_cards().equals("STAGE")) {
                            int m = pms.getCity().completedStages();
                            int l = ln.getCity().completedStages();
                            int r = rn.getCity().completedStages();
                            pms.add_purplePoints((m + l + r) * amount);
                        }else{
                            int m = pms.getCity().calculate_type_cards(card.getTarget_cards());
                            int l = ln.getCity().calculate_type_cards(card.getTarget_cards());
                            int r = rn.getCity().calculate_type_cards(card.getTarget_cards());
                            pms.add_balance((m + l + r) * amount);
                        }
                    }
                }
                break;
            }
            default:
                if (card.getEffect().contains("/")) {
                    extractResources(card.getEffect(), pms.getCity());
                } else {
                    pms.getCity().addResource(card.getEffect());
                }
                break;
        }
    }

    public static void stageEffect(String effect, PlayerMatchState pms) {
        if (effect.contains("&")) {
            String[] effects = effect.split("&");
            for (String s : effects) {
                // DISCARD, FREECARD, LASTCARD, COPYGUILD
                switch (s) {
                    case "DISCARD" -> {
                        pms.discard = true;
                        continue;
                    }
                    case "LASTCARD" -> {
                        pms.lastCard = true;
                        continue;
                    }
                    case "COPYGUILD" -> {
                        pms.copyGuild = true;
                        continue;
                    }
                    case "FREECARD" -> {
                        pms.freecard = true;
                        continue;
                    }
                }
                String[] r = s.split(":");
                pms.addPoints(Integer.parseInt(r[1]), r[0]);
            }
        }
        if (effect.contains("/")) {
            extractResources(effect, pms.getCity());
        } else {
            switch (effect) {
                case "DISCARD" -> {
                    pms.discard = true;
                    return;
                }
                case "LASTCARD" -> {
                    pms.lastCard = true;
                    return;
                }
                case "COPYGUILD" -> {
                    pms.copyGuild = true;
                    return;
                }
                case "FREECARD" -> {
                    pms.freecard = true;
                    return;
                }
            }
            String[] e = effect.split(":");
            pms.addPoints(Integer.parseInt(e[1]), e[0]);
        }
    }

    public static void extractResources(String effect, City city) {
        String[] effects = effect.split("/");
        for (String s : effects) {
            city.addResource(s);
        }
    }

}
