```kotlin


    // COMMANDS
    val context = Commands.createValidationContext(VanillaRegistries.createLookup())
    command("notify") {
        requiresPermissionLevel(1)
        argument<ItemInput>("item", ItemArgument.item(context)) { item ->
            argument<String>("type") { type ->
                suggestList { listOf("TASK", "GOAL", "CHALLENGE") }
                argument<String>("text", StringArgumentType.greedyString()) { text ->
                    runs {
                        val remappedType = when(type.invoke(this)) {
                            "TASK" -> AdvancementType.TASK
                            "GOAL" -> AdvancementType.GOAL
                            "CHALLENGE" -> AdvancementType.CHALLENGE
                            else -> throw IllegalArgumentException()
                        }
                        source.playerOrException.sendNotifcation(literalText(text.invoke(this)), item.invoke(this).createItemStack(1, false), remappedType)
                    }
                }
            }
        }
    }
    command("gustaf-advancements") {
        requiresPermissionLevel(1)
        argument<String>("advancement", StringArgumentType.greedyString()) { advancement ->
            suggestList { Advancements.advancements().map { it.id.toString() } }
            runs {
                mcCoroutineScope.launch {
                    Advancements.awardAdvancement(source.playerOrException, Advancements.advancement(ResourceLocation.parse(advancement.invoke(this@runs)))!!)
                }
            }
        }
    }
    command("serialize-item-test") {
        requiresPermissionLevel(1)
        runs {
            val item = ItemStackHolder(source.playerOrException.mainHandItem)
            source.sendSystemMessage(literalText(Json.encodeToString(item)))
        }
    }

```