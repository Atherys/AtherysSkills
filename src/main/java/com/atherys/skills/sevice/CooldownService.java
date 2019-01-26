package com.atherys.skills.sevice;

import com.google.inject.Singleton;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Singleton
public class CooldownService {

    private Map<UUID,Long> globalCooldowns = new HashMap<>();



}
