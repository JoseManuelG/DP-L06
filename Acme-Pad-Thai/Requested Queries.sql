

-Query C/1: The minimum, the average, and the maximum number of recipes per user.
	
	Ésta consulta selecciona todos los usuarios y luego calcula el mínimo, la media y el máximo del tamaño de la lista de recetas de cada usuario.

	select min(u.recipes.size),avg(u.recipes.size),max(u.recipes.size) from User u;
	
    	1 objects found
    Object #0 = [Ljava.lang.Object;{
    	{1, 2.5, 4}
    } 


-Query C/2: The user/s who has/have authored more recipes.

	Ésta consulta selecciona todos los usuarios cuando el tamaño de su lista de recetas es igual al tamaño máximo de la lista de recetas de un usuario.	

	select u from User u where u.recipes.size = (select max(uu.recipes.size) from User uu);

        1 objects found
    Object #0 = domain.User{
    	id=57
    	version=0
    	name="user2"
    	surname="surname2"
    	email="user2@email.com"
    	phone="123456788"
    	address=<null>
    	folders=[]
    	socialIdentities=[]
    	attends=[domain.Attend@42, domain.Attend@46]
    	userAccount=security.UserAccount@3
    	qualifications=[domain.Qualification@5e]
    	comments=[]
    	followers=[domain.Follow@90]
    	followeds=[]
    	recipes=[domain.Recipe@4d, domain.Recipe@4e, domain.Recipe@4f, domain.Recipe@50]
    } 

-Query C/3: The minimum, the average, and the maximum number of recipes that have qualified for a contest.

	Ésta consulta selecciona todos los concursos y luego calcula el mínimo, la media y el máximo del tamaño de la lista de recetas inscritas en cada concurso.
	
	select min(c.qualifieds.size),avg(c.qualifieds.size),max(c.qualifieds.size) from Contest c;
	
    	1 objects found
    Object #0 = [Ljava.lang.Object;{
    	{2, 3.0, 4}
    } 


-Query C/4: The contest/s for which more recipes has/have qualified.

	Ésta consulta selecciona los concursos cuya lista de recetas inscritas es igual al tamaño máximo de la lista de recetas de un concurso.	

	
	select c from Contest c where c.qualifieds.size = (select max(cc.qualifieds.size) from Contest cc);
	
    	1 objects found
    Object #0 = domain.Contest{
    	id=88
    	version=0
    	tittle="tittle2"
    	openingTime=<<2016-01-02 00:00:00.0>>
    	closingTime=<<2016-01-03 00:00:00.0>>
    	qualifieds=[domain.Qualified@78, domain.Qualified@76, domain.Qualified@77, domain.Qualified@79]
    } 


-Query C/5: The average and the standard deviation of number of steps per recipe.

	Ésta consulta selecciona todas las recetas y luego calcula la media y la variación estándar del tamaño de sus listas de pasos.
	
	select avg(s.steps.size),stddev(s.steps.size) from Recipe s;
	
    1 objects found
    Object #0 = [Ljava.lang.Object;{
    	{2.4, 1.0198}
    } 

-Query C/6: The average and the standard deviation of number of ingredients per recipe.

	Ésta consulta selecciona todas las recetas y luego calcula la media y la variación estándar del tamaño de sus listas de cantidades de ingredientes.
	
	select avg(s.quantities.size),stddev(s.quantities.size) from Recipe s;
	
	1 objects found
    Object #0 = [Ljava.lang.Object;{
	{0.2, 0.4}
    } 


-Query C/7: A listing of users in descending order of popularity.

	Ésta consulta selecciona todos los usuarios y los ordena de mayor a menor tamaño de su lista de seguidores.

	select s from User s order by s.followers.size DESC;
	
	2 objects found
    Object #0 = domain.User{
    	id=57
    	version=0
    	name="user2"
    	surname="surname2"
    	email="user2@email.com"
    	phone="123456788"
    	address=<null>
    	folders=[]
    	socialIdentities=[]
    	attends=[domain.Attend@42, domain.Attend@46]
    	userAccount=security.UserAccount@3
    	qualifications=[domain.Qualification@5e]
    	comments=[]
    	followers=[domain.Follow@90]
    	followeds=[]
    	recipes=[domain.Recipe@4d, domain.Recipe@4e, domain.Recipe@4f, domain.Recipe@50]
    } 
    Object #1 = domain.User{
    	id=56
    	version=0
    	name="user1"
    	surname="surname1"
    	email="user1@email.com"
    	phone="123456789"
    	address=<null>
    	folders=[]
    	socialIdentities=[]
    	attends=[domain.Attend@40, domain.Attend@44]
    	userAccount=security.UserAccount@2
    	qualifications=[domain.Qualification@5d, domain.Qualification@60, domain.Qualification@61]
    	comments=[]
    	followers=[]
    	followeds=[domain.Follow@90, domain.Follow@92]
    	recipes=[domain.Recipe@4c]
    } 


-Query C/8: A listing of users in descending order regarding the average number of likes and dislikes that their recipes get.

	Ésta consulta selecciona todas las recetas desde ahí sus usuarios y calificaciones y las ordena de forma descendente por su media de calificación.
	
	select r.user from Recipe r LEFT JOIN r.qualifications q group by r.user order by avg(q.qualification) DESC;
	
    	2 objects found
    Object #0 = domain.User{
    	id=56
    	version=0
    	name="user1"
    	surname="surname1"
    	email="user1@email.com"
    	phone="123456789"
    	address=<null>
    	folders=[]
    	socialIdentities=[]
    	attends=[domain.Attend@40, domain.Attend@44]
    	userAccount=security.UserAccount@2
    	qualifications=[domain.Qualification@5d, domain.Qualification@60, domain.Qualification@61]
    	comments=[]
    	followers=[]
    	followeds=[domain.Follow@90, domain.Follow@92]
    	recipes=[domain.Recipe@4c]
    } 
    Object #1 = domain.User{
    	id=57
    	version=0
    	name="user2"
    	surname="surname2"
    	email="user2@email.com"
    	phone="123456788"
    	address=<null>
    	folders=[]
    	socialIdentities=[]
    	attends=[domain.Attend@42, domain.Attend@46]
    	userAccount=security.UserAccount@3
    	qualifications=[domain.Qualification@5e]
    	comments=[]
    	followers=[domain.Follow@90]
    	followeds=[]
    	recipes=[domain.Recipe@4d, domain.Recipe@4e, domain.Recipe@4f, domain.Recipe@50]
    } 


-Query B/1: The minimum, the average, and the maximum number of campaigns per sponsor.

	Ésta consulta selecciona todos los sponsors y luego calcula el mínimo, la media y el máximo del tamaño de la lista de campañas de cada sponsor.

	select min(s.campaigns.size),avg(s.campaigns.size),max(s.campaigns.size) from Sponsor s;
	
    	1 objects found
    Object #0 = [Ljava.lang.Object;{
    	{1, 1.25, 2}
    }


-Query B/2: The minimum, the average, and the maximum number of active campaigns per sponsor.(sera filtrada mas adelante desde servicios)
	
	Ésta consulta es la más cercana que hemos podido hacer a lo que se pide. Ccomo no resuelve la funcionalidad pedida, hemos decidido resolver dicha funcionalidad más adelante, en la capa de servicios.

	select min(s.campaigns.size),avg(s.campaigns.size),max(s.campaigns.size) from Sponsor s;


-Query B/3: The ranking of companies according the number of campaigns that theyve organised via their sponsors.

	Ésta consulta selecciona todos los sponsors y desde ahí sus cumpañías. Luego ordena dichas compañías por el tamaño de la lista de campañas de cada sponsor en orden descendiente.

	select s.companyName from Sponsor s order by s.campaigns.size DESC;
	
    4 objects found
    Object #0 = java.lang.String{"companyName1"} 
    Object #1 = java.lang.String{"companyName2"} 
    Object #2 = java.lang.String{"companyName3"} 
    Object #3 = java.lang.String{"companyName4"} 

-Query B/4: The ranking of companies according their monthly bills.

	Ésta consulta selecciona todos los sponsors y desde ahí sus cumpañías. Luego ordena dichas compañías por el tamaño de la lista de facturas de cada sponsor en orden descendiente.

	select s.companyName from Sponsor s order by s.bills.size DESC;
	
    4 objects found
    Object #0 = java.lang.String{"companyName1"} 
    Object #1 = java.lang.String{"companyName2"} 
    Object #2 = java.lang.String{"companyName3"} 
    Object #3 = java.lang.String{"companyName4"} 

-Query B/5: The average and the standard deviation of paid and unpaid monthly bills.

	Ésta consulta selecciona todas las facturas y calcula la media y la variación estándar de las facturas pagadas. Luego, hace lo mismo con las facturas no pagadas.

	select avg(b.cost),stddev(b.cost),avg(c.cost),stddev(c.cost) from Bill b, Bill c where b.dateOfPay is not null and c.dateOfPay is null;
	
       1 objects found
    Object #0 = [Ljava.lang.Object;{
    	{88.08333333333333, 10.12491426575499, 87.5, 7.5}
    } 


-Query B/6: The sponsors who have not managed a campaign for the last three months.

	Ésta consulta selecciona todos los sponsors que no están en el grupo siguiente: los sponsors que tienen campañas cuya fecha final está a menos de 90 días desde hoy.

    select ss from Sponsor ss where ss not in (select s from Sponsor s, IN(s.campaigns) b where not b.dateOfEnd < CURRENT_DATE-90);
    
    1 objects found
    Object #0 = domain.Sponsor{
    	id=19
    	version=0
    	name="name4"
    	surname="surname4"
    	email="sponsor4@email.com"
    	phone="999999888"
    	address="address4"
    	folders=[]
    	socialIdentities=[]
    	attends=[]
    	userAccount=security.UserAccount@b
    	companyName="companyName4"
    	creditCards=[domain.CreditCard@f]
    	bills=[domain.Bill@1d]
    	campaigns=[domain.Campaign@18]
    } 
-Query B/7: The companies that have spent less than the average in their campaigns.

	Ésta consulta selecciona todas las compañías de los sponsors que están en la siguiente lista: sponsors cuya media de coste de su factura es menor que la media de coste de todas las facturas.

	select s.companyName from Sponsor s where (select avg(b.cost) from Bill b) > (select avg(t.cost) from Bill t where t.sponsor=s);

    1 objects found
    Object #0 = java.lang.String{"companyName4"} 

-Query B/8: The companies that have spent at least 90% the maximum amount of money that a company has spent on a campaign.

	Ésta consulta selecciona todas las compañías de los sponsors que cumplen la siguiente condición: tienen facturas con un coste mayor que el 90% del coste máximo de una factura.

	select s.companyName from Sponsor s where (select max(b.cost) from Bill b where b.sponsor = s) > 0.9*(select max(bb.cost) from Bill bb);
	
    2 objects found
    Object #0 = java.lang.String{"companyName1"} 
    Object #1 = java.lang.String{"companyName2"} 

-Query A/1: The minimum, the maximum, the average, and the standard deviation of the number of master classes per cook.

	Ésta consulta selecciona todos los cocineros y luego calcula el mínimo, el máximo y la media del tamaño de su lista de clases de cada uno.

	select min(s.masterClasses.size),max(s.masterClasses.size),avg(s.masterClasses.size),stddev(s.masterClasses.size) from Cook s;
	
    1 objects found
    Object #0 = [Ljava.lang.Object;{
    	{1, 2, 1.5, 0.5}
    	
-Query A/2: The average number of learning materials per master class, grouped by kind of learning material.

	Ésta consulta calcula la media de learningMaterials de cada tipo entre todas las masterClasses
	
    select count(l)*1.0/(select count(m) from MasterClass m),l.type from LearningMaterial l group by l.type; 
    
        3 objects found
    Object #0 = [Ljava.lang.Object;{
    	{0.66667, "presentation"}
    } 
    Object #1 = [Ljava.lang.Object;{
    	{1.33333, "text"}
    } 
    Object #2 = [Ljava.lang.Object;{
    	{1.0, "video"}
    } 
    
-Query A/3: The number of master classes that have been promoted.


	select count(c) from MasterClass c where c.promoted = true;
	
	    1 objects found
    Object #0 = java.lang.Long{2} 


-Query A/4: The listing of cooks, sorted according to the number of master classes that have been promoted.

	Ésta consulta selecciona todos los cocineros que imparten una clase, ordenados por una cuenta del número de clases que imparten que han sido promovidas.


	select r.cook from MasterClass r group by r.cook order by count(r.promoted) DESC;
	
        2 objects found
    Object #0 = domain.Cook{
    	id=41
    	version=0
    	name="Cook1"
    	surname="surname1"
    	email="cook1@email.com"
    	phone="123456789"
    	address=<null>
    	folders=[]
    	socialIdentities=[]
    	attends=[]
    	userAccount=security.UserAccount@5
    	qualifications=[]
    	comments=[]
    	followers=[]
    	followeds=[]
    	masterClasses=[domain.MasterClass@2b, domain.MasterClass@2d]
    } 
    Object #1 = domain.Cook{
    	id=42
    	version=0
    	name="Cook2"
    	surname="surname2"
    	email="cook2@email.com"
    	phone="234567890"
    	address=<null>
    	folders=[]
    	socialIdentities=[]
    	attends=[domain.Attend@48]
    	userAccount=security.UserAccount@6
    	qualifications=[]
    	comments=[]
    	followers=[]
    	followeds=[]
    	masterClasses=[domain.MasterClass@2c]
    } 

-Query A/5: The average number of promoted and demoted master classes per cook.

	Ésta consulta selecciona dentro de los cocineros la media de clases promoted y calcula la media de demoted segun las promoted

	select avg(m.promoted),1-avg(m.promoted) from Cook c JOIN c.masterClasses m group by c;
	
    	2 objects found
    Object #0 = [Ljava.lang.Object;{
    	{1.0, 0.0}
    } 
    Object #1 = [Ljava.lang.Object;{
    	{0.0, 1.0}
    } 

