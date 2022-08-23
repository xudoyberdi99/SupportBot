package on.insurance.supportbot


import on.insurance.supportbot.teligram.MessageEntity
import on.insurance.supportbot.teligram.User
import org.springframework.util.FileCopyUtils
import org.springframework.web.bind.annotation.*
import java.io.FileInputStream
import java.io.IOException
import javax.servlet.http.HttpServletResponse


@RestController
@RequestMapping("/api/v1/operator")
class OperatorController(
    private val service: OperatorService,
    private val userService: UserService,
    private val groupService: GroupService,
    private val messageService: MessageService
    ) {
    @PostMapping
    fun create(@RequestBody dto: OperatorCreateDto) = service.create(dto)

    @GetMapping("{id}")
    fun get(@PathVariable id: Long): OperatorDto = service.get(id)

    @PutMapping("{id}")
    fun update(@PathVariable id: Long, @RequestBody dto: OperatorUpdateDto) = service.update(id, dto)

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long) = service.delete(id)

    @GetMapping("operatorlist")
    fun getAllList(): List<User> = userService.operatorList()

    @GetMapping("groupList")
    fun getAllGroupList(@RequestBody dto: GroupsByOperatorIdDto): List<GroupsByOperatorId> = groupService.groupsByOperatorId(dto)

    @GetMapping("messageList/{groupId}")
    fun getAllMessageList(@PathVariable groupId:Long):List<MessageEntity> = messageService.getAllMessageByGroupId(groupId)

}

@RestController
@RequestMapping("/api/v1/document")
class DocumentController(
    val messageRepository: MessageRepository
){
    val document: String ="document"

//
//    @GetMapping("getFromFileSystem/{id}")
//    @Throws(IOException::class)
//    fun getFromFileSystem(@PathVariable id: Long, response: HttpServletResponse) {
//        val byId: Optional<Attachment> = attachmentRepository.findById(id)
//        if (byId.isPresent()) {
//            val attachment: Attachment = byId.get()
//            response.setHeader(
//                "Content-Disposition",
//                "attachment; file=\"" + attachment.getFileOriginalName().toString() + "\""
//            )
//            response.contentType = attachment.getContentType()
//            val fileInputStream = FileInputStream(document.toString() + "/" + attachment.getName())
//            FileCopyUtils.copy(fileInputStream, response.outputStream)
//        }
//    }


}
