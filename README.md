# CommandAPI

## Setup/Usage
### Initalization
```java
CommandManager manager = new CommandManager(client, "my.toplevel.package");
```
By creating a new command manager object, you are registering all of your commands under the `my.toplevel.package`. All subpackages will also be serached for commands and registered.

### Command Creation
```java
@BotCommand(command = "ping", description = "Get a response from the bot", usage = "ping", module = "Fun Commands")
public static void helpCommand(CommandContext cc) {
    cc.replyWith("pong!");
}
```
This is the basic setup for a command. The method **must** be static and its only parameter must be CommandContext. The access modifier, return type, method name all do not matter. Each required argument of the `@BotCommand` annotation is described below.
 - command: The command typed by the user. Can use an array such as `command = { "tag", "add" }` to create subcommands.
 - description: The description of what the command does. This information is used by the `!Help` command.
 - usage: This is how the command is used. There is no required format here, but this will be used by the `!Help` command to show users how to use the command.
 - module: This is used to group commands together in the `!Help` command.
 
## Dependency Management
Check out [JitPack](https://jitpack.io/#DiscordBolt/CommandAPI) for instructions on how to incorporate this into your project.
