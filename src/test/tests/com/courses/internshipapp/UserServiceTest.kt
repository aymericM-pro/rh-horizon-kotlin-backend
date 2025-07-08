package com.courses.internshipapp

import com.courses.internshipapp.core.errors.BusinessException
import com.courses.internshipapp.modules.roles.Role
import com.courses.internshipapp.modules.students.StudentEntity
import com.courses.internshipapp.modules.users.ErrorUser
import com.courses.internshipapp.modules.users.UserEntity
import com.courses.internshipapp.modules.users.UserRepository
import com.courses.internshipapp.modules.users.UserService
import com.courses.internshipapp.modules.users.dtos.UserCreate
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class UserServiceTest {

    @Mock
    lateinit var userRepository: UserRepository;

    @InjectMocks
    lateinit var userService: UserService

    @BeforeEach
    fun setup() {
        reset(userRepository)
    }

    @Test
    fun should_getUserById() {
        val user = StudentEntity(
            name = "Aymeric",
            email = "aymeric.maillot@gmail.com",
            password = "password"
        )
        user.userId = UUID.randomUUID()

        `when`(userRepository.findById(user.userId!!)).thenReturn(Optional.of(user))

        val result = userService.getUserById(user.userId!!)

        assertEquals(user.name, result.name)
        assertEquals(user.email, result.email)
        assertEquals(user.role, result.role)
    }

    @Test
    fun should_createUser() {
        val user = StudentEntity(
            name = "Aymeric",
            email = "aymeric.maillot@gmail.com",
            password = "password"
        )
        user.userId = UUID.randomUUID()

        val userRequest = UserCreate(
            name = "Aymeric",
            "aymeric.maillot@gmail.com",
            "password",
            Role.STUDENT
        )

        `when`(userRepository.save(any())).thenReturn(user)

        val result = userService.createUser(userRequest);

        assertEquals(result.userId, user.userId)
        assertEquals(result.name, userRequest.name)
        assertEquals(result.email, userRequest.email)
        assertEquals(result.role, userRequest.role)
    }

    @Test
    fun should_getAllUsers(){
        val user = StudentEntity(
            name = "Aymeric",
            email = "aymeric.maillot@gmail.com",
            password = "password"
        )
        user.userId = UUID.randomUUID()

        val userTwo = StudentEntity(
            name = "Tata",
            email = "tata@gmail.com",
            password = "tatatata"
        )
        userTwo.userId = UUID.randomUUID()

        `when`(userRepository.findAll()).thenReturn(listOf(user, userTwo));

        val result = userService.getAllUsers();

        assertEquals(2, result.size)
        assertEquals(user.name, result[0].name)
        assertEquals(user.email, result[0].email)
        assertEquals(user.role, result[0].role)
        assertEquals(userTwo.name, result[1].name)
        assertEquals(userTwo.email, result[1].email)
        assertEquals(userTwo.role, result[1].role)
    }

    @Test
    fun should_deleteUserById() {
        val id = UUID.randomUUID()

        userService.deleteUser(id)

        verify(userRepository, times(1)).deleteById(id)
    }

    @Test
    fun should_throwWhen_emailAlreadyExist() {
        val userRequest = UserCreate(
            name = "Aymeric",
            email = "aymeric.maillot@gmail.com",
            password = "password",
            role = Role.STUDENT
        )

        `when`(userRepository.existsByEmail(userRequest.email)).thenReturn(true)

        val exception = assertThrows<BusinessException> {
            userService.createUser(userRequest)
        }

        assertEquals(ErrorUser.EMAIL_ALREADY_EXISTS, exception.error)
    }

    @Test
    fun should_throw_when_user_not_found_by_id() {
        val id = UUID.randomUUID()

        `when`(userRepository.findById(id)).thenReturn(Optional.empty())

        val exception = assertThrows<BusinessException> {
            userService.getUserById(id)
        }

        assertEquals(ErrorUser.USER_NOT_FOUND, exception.error)
    }

    @Test
    fun should_throw_when_no_users_found() {
        `when`(userRepository.findAll()).thenReturn(emptyList())

        val exception = assertThrows<BusinessException> {
            userService.getAllUsers()
        }

        assertEquals(ErrorUser.USER_NOT_FOUND, exception.error)
    }
}