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

            String query = "select t from Team t";
            List<Team> result = em.createQuery(query, Team.class).setFirstResult(0)
                    .setMaxResults(2).getResultList();

            System.out.println("result = " + result.size());

            for (Team team : result) {
                System.out.println("team = " + team.getName() + "|" + team.getMembers().size());
                for(Member member : team.getMembers()){
                    System.out.println("member = " + member);
                }
            }

            tx.commit();
        }catch (Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally {
            em.close();
        }
    }
}
