## Документация api

Все методы представляют собой GET запросы и в качестве ответа возвращают текст.

### Аутентификация

Есть две роли: студент и преподаватель. Тестовый аккаунт студента: test@email.com пароль passW0. Тестовый аккаунт
преподавателя: mbonsul0@scribd.com пароль unW4P2LAK4T0

```
/login?email={...}&password={...} - вход в систему 
/signup?vercode={...}&email={...}&password={...} - регистрация по заранее выданному верификацонному коду
/update/password?oldPassword={...}&newPassword={...} - обновление пароля
```

### Управление профилем

```
/profile - просмотр своего профиля                          
/profile/another?email={...} - просмотр чужого профиля
/profile/update/phone?phone={...} - изменение телефона
/profile/update/town?town={...} - изменение города
/profile/update/info?info={...} - изменение информации о себе
/profile/update/vk?link={...} - изменение ссылки на vk
/profile/update/facebook?link={...} - изменение ссылки на facebook
/profile/update/linkedin?link={...} - изменение ссылки на linkedin
/profile/update/instagram?link={...} - изменение ссылки на instagram
/groupmates - просмотр своих одногруппников
```

### Управление курсами и домашними заданиями

```
/course/list - просмотр списка курсов                      
/course?id={...} - просмотр определённого курса
/course/material/add?courseId={...}&name={...}&content={...} - добавление материала курса
/course/material/delete?id={...} - удаление материала курса
/course/material/update?id=={...}&name={...}&content={...} - обновление материала курса
/course/hometask/add?courseId={...}&name={...}&startDate={...}&finishDate={...}&description={...} - добавление домашнего задания
/course/hometask/delete?id={...} - удаление домашнего задания
/course/hometask/update?id={...}&name={...}&startDate={...}&finishDate={...}&description={...} - обновление домашнего задания
/course/groupleader/add?courseId={...}&email={...} - добавление старосты
/course/groupleader/delete?courseId={...}&email={...} - удаление старосты
/hometask/solutions?id={...} - просмотр присланных решений домашнего задания
/hometask/solution/update?id={...}&content={...} - отправка решения домашнего задания
```
