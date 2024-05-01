```java
private void registerCommands() {
        int amount = 0;
        final String packet = getClass().getPackageName();
        for (Class<? extends CustomCommand> clazz : new Reflections(
                packet + ".command").getSubTypesOf(CustomCommand.class)
        ) {
            try {
                final CustomCommand command = clazz.getDeclaredConstructor().newInstance();
                this.getServer().getCommandMap().register(
                        command.getLabel(), this.getName(), command);
                amount += 1;
            } catch (NoSuchMethodException | InvocationTargetException
                     | InstantiationException | IllegalAccessException exception
            ) {
                this.getSLF4JLogger().error("[{}] Failed to load commands.",
                        exception.getClass().getName(), exception
                );
                this.getServer().getPluginManager().disablePlugin(this);
            }
        } this.getSLF4JLogger().info("Successfully loaded {} commands.", amount);
    }
```
