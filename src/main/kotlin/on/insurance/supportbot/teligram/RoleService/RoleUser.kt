package on.insurance.supportbot.teligram.RoleService

import on.insurance.supportbot.GroupService
import on.insurance.supportbot.MessageService
import on.insurance.supportbot.UserService
import on.insurance.supportbot.teligram.*
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow

@Service
class RoleUser(
    @Lazy
    val botService: BotService,
    val groupService: GroupService,
    val messageService: MessageService,
    val roleOperator: RoleOperator,
    val userService: UserService,
) {
    lateinit var update: Update
    lateinit var user: User
    lateinit var group: Group

    fun userFunc(updateFunc: Update, userFunc: User) {
        update = updateFunc
        user = userFunc
        group = groupService.getGroupByUserId(user)

        scanButton(update.message.text)
        when (user.botStep) {
            BotStep.CHAT -> {
                saveChat()
                sendText()
            }
            BotStep.QUEUE->{
                botService.sendMassage(update.message.chatId,"${group.operator?.run { "0" }?:{"10"}} navbattasiz",queueButton(""))
                user.botStep=BotStep.CHAT
            }
        }
        userService.update(user)

    }

    fun saveChat() {
        val text = update.message.text
        messageService.creat(text, group, user,group.operator!=null)
    }

    fun sendText() {
        var chatId: Long = 0
        var text: String = ""
        update.message?.run {
            chatId = getChatId()
            text = getText()
        }
        group.operator?.run { botService.sendMassage(this.chatId, text,roleOperator.menuButton("")) }
    }

    fun queueButton(lang: String): ReplyKeyboardMarkup = ReplyKeyboardMarkup().apply {
        oneTimeKeyboard = true
        resizeKeyboard = true
        selective = false
        keyboard = mutableListOf(KeyboardRow(listOf(
            KeyboardButton().apply {
                text = "navbatingizni bilish"
                requestContact = false
            }
        )))

    }

    fun scanButton(text:String){
        when(text){
            "navbatingizni bilish"->{
                user.botStep=BotStep.QUEUE
            }
        }
    }


}