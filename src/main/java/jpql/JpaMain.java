package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        try{
            Team teamA = new Team();
            teamA.setName("teamA");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("teamB");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUserName("member1");
            member1.setType(MemberType.ADMIN);

            Member member2 = new Member();
            member2.setUserName("member2");
            member2.setType(MemberType.ADMIN);

            Member member3 = new Member();
            member3.setUserName("member3");
            member3.setType(MemberType.ADMIN);

            member1.setTeam(teamA);
            member2.setTeam(teamA);
            member3.setTeam(teamB);

            em.persist(member1);
            em.persist(member2);
            em.persist(member3);
            
            em.flush();
            em.clear();

            int resultCount = em.createQuery("update Member m set m.age = 20").executeUpdate();
            System.out.println("resultCount = " + resultCount);
            em.clear(); // createQuery는 flush를 시키지만 clear을 따로하지 않아 만약 member1.getAge를 하게되면 영속성에서 가져오기 때문에 0이 출력됨


            /*List<Member> result = em.createNamedQuery("Member.findByUserName", Member.class)
                    .setParameter("username", "member1").getResultList();
            for (Member member : result) {
                System.out.println("member = " + member);
            }*/

            tx.commit();
        }catch (Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally {
            em.close();
        }
    }
}
