package net.retronixmc.coins.utils;

import net.retronixmc.coins.profile.Profile;

import java.util.Comparator;
import java.util.HashMap;

public class MobcoinTop  implements Comparator<Profile> {
    HashMap<Profile, Integer> base;
    public MobcoinTop(HashMap<Profile, Integer> base) {
        this.base = base;
    }

    public int compare(Profile a, Profile b) {
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        }
    }
}
