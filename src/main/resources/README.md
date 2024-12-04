prompt1-getAll
生成parkingRepository类，并不需要访问数据库，只需要访问内部属性1.1属性有3个parkingLot的一个list,容量分别为9，12，9，名字分别为the Plaza Park，City Mall Garage，Office Tower Parking,1.2属性有3个parkingboy的一个list，分别是用不同策略的parking boy，2.1方法返回所有parkinglot的方法，无返回值
生成parkingservice类1.1属性有注入的parkingRepository，2.1方法返回所有parkinglot的方法，调用parkingRepository的对应方法
我需要一个统一的返回结果包装类，去包装结果
创建controller文件，属性有parkingservice注入，方法有获取所有的parkinglots，get请求，总的controller的访问路径是“/parking-lot”
创建一个ParkingLotApiTest测试，并生成获取全部parkinglot的测试，要有数据填入，然后验证获取的是否正确，名称为should_when_given的格式,使用.andExpect(jsonPath("$.director.name").value(director.getName())) .andExpect(jsonPath("$.director.age").value(director.getAge()));这样去判断parkingLot里面的tickets数据