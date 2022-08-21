package on.insurance.supportbot


import on.insurance.supportbot.teligram.Group
import on.insurance.supportbot.teligram.MessageEntity
import on.insurance.supportbot.teligram.User
import org.springframework.web.bind.annotation.*


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

    @GetMapping("groupList/{operatorId}")
    fun getAllGroupList(@PathVariable operatorId: Long): List<Group> = groupService.getAllGroupListByOperatorId(operatorId)

    @GetMapping("messageList/{groupId}")
    fun getAllMessageList(@PathVariable groupId:Long):List<MessageEntity> = messageService.getAllMessageByGroupId(groupId)
}
