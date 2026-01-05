import React, { useEffect, useMemo, useState } from "react";
import {
  Container,
  Typography,
  Grid,
  Box,
  Card,
  CardContent,
  CardMedia,
  IconButton,
  useTheme,
  useMediaQuery,
} from "@mui/material";
import {
  ArrowDownward,
  LinkedIn,
} from "@mui/icons-material";
import AOS from "aos";
import "aos/dist/aos.css";
import Mrudul from '../../assets/sneha.jpeg'
import college from '../../assets/college.png'
import Mahesh from '../../assets/Mahesh.jpg'

const About = () => {
  const themes = useTheme();
  const isMobile = useMediaQuery(themes.breakpoints.down("sm"));
  const [zoom, setZoom] = useState(1);

  useEffect(() => {
    AOS.init({ duration: 1000 });
    window.scrollTo({ top: 0, behavior: "smooth" });
  }, []);

  useEffect(() => {
    const interval = setInterval(() => {
      setZoom((prevZoom) => (prevZoom === 1 ? 1.2 : 1));
    }, 5000);
    return () => clearInterval(interval);
  }, []);

  const transformStyle = useMemo(
    () => ({
      transform: `scale(${zoom})`,
      transition: "transform 5s ease-in-out",
      // backgroundImage: `linear-gradient(rgba(0, 0, 0, 0.6), rgba(0, 0, 0, 0.6)),url(${})`,
    }),
    [zoom]
  );


  const teamMembers = [
   
    {
      name: "Miss.Sneha Dhere",
      position: "Frontend Developer/backend Developer",
      college: "Shankar Narayan College of Arts & Commerce",
      departmant: "Information Technology",
      image: sneha,
    },
    {
      name: "Mr.Mahesh Ghalme",
      position: "Database Developer/DevOps Engineer",
      college: "Shankar Narayan College of Arts & Commerce",
      departmant: "Information Technology",
      image: Mahesh,
    },
    
    // {
    //   name: "Miss.Sneha Dhere",
    //   position: "Frontend Developer/Backend Developer",
    //   college: "Shankar Narayan College of Arts & Commerce",
    //   departmant: "Information Technology",
    //   image: ,
    // },
  ];

  return (
    <Box className="overflow-hidden mt-1.2 text-white">


      {/* Our Story Section */}
      <Container maxWidth="lg" className="py-12 md:py-20">
        <Grid container spacing={6} alignItems="center">
          <Grid item xs={12} md={6} data-aos="fade-right">
            <Box className="w-[100%] h-[100%] flex items-center justify-center" >
              <Box
                component="img"
                src={college}
                alt="Our Story"
                className="w-56 h-56  rounded-lg shadow-lg object-cover  "
              />
            </Box>
          </Grid>
          <Grid item xs={12} md={6} data-aos="fade-left">

            <Typography className="font-bold mb-3 text-yellow-500" variant="h4" component="h2" >
           Shri Shankar Narayan Education Trust's College of Arts Commerce
            </Typography>
            <Typography className="mb-4" variant="body1">
             Shankar Narayan College of Arts and Commerce, established in 1994, located in Thane is a private college. It is affiliated with the University of Mumbai and is accredited by NAAC with an 'A' grade. 

The college offers regular courses like B.Com, B.A., B.Sc and Professional Courses like BMS, BBI, BAF, B.Sc.I.T. and BFM. The admission is done both through online and offline processes. The selection process is done on the basis of merit of the last qualifying examination.
            </Typography>

          </Grid>
        </Grid>
      </Container>

      {/* Accommodations Section */}
      <Container maxWidth="lg">
        <Box className="text-center mb-12" data-aos="fade-up">
          <Typography className="text-yellow-400 tracking-widest" variant="overline" fontSize={40}>
            Vision
          </Typography>
          <Typography className="mb-2 flex justify-center items-centerm text-justify:" variant="h5" component="h2">
            To empower individuals with the knowledge, skills, and confidence to navigate the complex world of cryptocurrency trading through an accessible, risk-free, and interactive demo trading platform. By simulating real-market conditions with virtual funds, we aim to foster financial literacy, encourage strategic decision-making, and inspire a new generation of informed traders and investors.
          </Typography>
        </Box>

        <Grid container spacing={4}>

        </Grid>
      </Container>

      {/* Team Section */}
      <Box className="py-12 md:py-20">
        <Container maxWidth="lg">
          <Box className="text-center mb-12" data-aos="fade-up">
            <Typography className="text-yellow-400 tracking-widest" variant="overline" fontSize={30}>
              OUR TEAM
            </Typography>
            <Typography className="flex justify-center items-center text-center" variant="h5" component="h2">
              The People Behind Saffron Stays
            </Typography>
          </Box>

          <Grid container spacing={4}>
            {teamMembers.map((member, index) => (
              <Grid item xs={10} sm={6} md={3} key={index} data-aos="fade-up" data-aos-delay={index * 100} >
                <Card className="h-full rounded-lg shadow-md overflow-hidden">
                  <Box className="relative pt-[100%]">
                    <CardMedia
                      component="img"
                      image={member.image}
                      alt={member.name}
                      className="absolute top-0 left-0 w-full h-full object-cover"
                    />
                  </Box>
                  <CardContent>
                    <Typography className="font-semibold" variant="h6" component="h3">
                      {member.name}
                    </Typography>
                    <Typography className="text-yellow-600 mb-1" variant="subtitle2">
                      {member.position}
                    </Typography>
                    <Typography variant="body2" className="text-gray-900">
                      {member.college}
                    </Typography>
                    <Typography variant="body2" className="text-yellow-600">
                      Department : {member.departmant}
                    </Typography>

                  </CardContent>
                </Card>
              </Grid>
            ))}
          </Grid>

        </Container>
      </Box>
      {/* Tech */}
      {/* Tech */}
      <Box maxWidth="lg">
        <Box className="text-center mb-12" data-aos="fade-up">
          <Typography className="text-yellow-400 tracking-widest" variant="overline" fontSize={40}>
            Technology
          </Typography>
        </Box>

        <Grid container spacing={4} justifyContent="center" data-aos="fade-up">
          {[
            'Java',
            'Spring Boot',
            'React JS',
            'MySQL',
            'Binance WebSocket',
          ].map((tech, index) => (
            <Grid item key={index}>
              <Typography
                sx={{
                  border: '1px solid white',
                  color: 'white',
                  px: 3,
                  py: 1,
                  borderRadius: '8px',
                  fontSize: '1.2rem',
                }}
              >
                {tech}
              </Typography>
            </Grid>
          ))}
        </Grid>
      </Box>
    </Box>
  );
};

export default About;
