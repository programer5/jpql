package hollejpq.jpql;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jqpl.Member;
import jqpl.Team;

@SpringBootApplication
public class JpqlApplication {

	
	public static void main(String[] args) {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
		EntityManager em = emf.createEntityManager();

		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		try {


				Team team = new Team();
				team.setName("teamA");
				em.persist(team);
				
				Member member = new Member();
				member.setUsername("member1");
				member.setAge(10);

				member.setTeam(team);

				em.persist(member);
			

			em.flush();
			em.clear();

			String query = "select m from Member m left join m.team t";
			em.createQuery(query, Member.class)
			.getResultList();


			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}

		emf.close();
		
		SpringApplication.run(JpqlApplication.class, args);
	}

}
