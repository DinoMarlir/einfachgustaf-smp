```kotlin
// ADVANCEMENTS
    val root = Advancements.createTab(
        GustafAdvancement(
            Items.WIND_CHARGE.defaultInstance,
            literalText("EinfachGustaf"),
            literalText("Test Advancements"),
            AdvancementType.TASK,
            ResourceLocation.withDefaultNamespace("textures/block/melon_side.png"),
            isUnlocked = true
        )
    )
    val welcome = Advancements.register(
        GustafAdvancement(
            Items.HONEYCOMB.defaultInstance,
            literalText("Bee welcome!"),
            literalText("We're glad to have you here!"),
            AdvancementType.TASK,
            isUnlocked = true,
            unlocks = setOf(Advancements.res("welcome/hello"), Advancements.res("welcome/claim"))
        ),
        "welcome",
        root,
        x = 1.5f,
    )
    val claim = Advancements.register(
        GustafAdvancement(Items.WHITE_BANNER.defaultInstance, literalText("Settler!"), literalText("Claim a chunk!"), AdvancementType.TASK, unlocks = setOf(Advancements.res("welcome/claim16")), rewards = setOf(Items.BOW.defaultInstance)),
        "welcome/claim",
        welcome,
        x = 3f,
        y = 0f,
    )
    val claim16 = Advancements.register(
        GustafAdvancement(Items.PINK_BANNER.defaultInstance, literalText("My Empire!"), literalText("Claim at least 16 chunks"), AdvancementType.CHALLENGE),
        "welcome/claim16",
        claim,
        x = 4f,
        y = 0f,
    )
    val hello = Advancements.register(
        GustafAdvancement(Items.OAK_BOAT.defaultInstance, literalText("Hello!"), literalText("Introduce yourself in the chat!"), AdvancementType.GOAL),
        "welcome/hello",
        welcome,
        x = 3f,
        y = 2f
    ) 
```