prompt1-getAll
生成parkingRepository类，并不需要访问数据库，只需要访问内部属性
1.1属性有3个parkingLot的一个list,容量分别为9，12，9，名字分别为the Plaza Park，City Mall Garage，Office Tower Parking,1.2属性有3个parkingboy的一个list，分别是用不同策略的parking boy
2.1方法返回所有parkinglot的方法，无返回值
生成parkingservice类
1.1属性有注入的parkingRepository
2.1方法返回所有parkinglot的方法，调用parkingRepository的对应方法
我需要一个统一的返回结果包装类，去包装结果
创建controller文件，属性有parkingservice注入，方法有获取所有的parkinglots，get请求，总的controller的访问路径是“/parking-lot”
创建一个ParkingLotApiTest测试，并生成获取全部parkinglot的测试，要有数据填入，然后验证获取的是否正确，名称为should_when_given的格式,使用.andExpect(jsonPath("$.director.name").value(director.getName())) .andExpect(jsonPath("$.director.age").value(director.getAge()));这样去判断parkingLot里面的tickets数据

prompt2-park
生成park方法，1.参数有Parkingboy type,Car,返回结果为ticket，方法体为根据type获取对应boy停车
生成postAPI，路径为/park，参数有car,parkingboytype，方法体调用service的对应方法
生成parkapi的3个测试，名称为should_when_given的格式，第一个parkingboy的策略是按顺序停，第二个parkingboy是按空位的数量，第三个parkingboy是按空位占总位置比，生成不同的前置条件，并验证不同parkingboy是否停在正确的位置
就按空位多的停的parkingboy，你需要在请求前，填充一下3个parkinglot，出现一个空位多的，然后发请求，得到的结果就是停在空位多的
给一个测试，若parkingtype不存在，判断是否返回异常信息

prompt3-fetch
生成fetch的方法，参数有ticket，返回car
生成fetchapi，路径为/fetch，参数有ticket，方法体调用service的对应方法
生成fetchapi的测试，名称为should_when_given的格式，判断返回的ticket是否正确