


RFC9- FUNCIONA

Select us.nombre, us.identificacion, count(*)
from usuario us
inner join reserva res
on us.identificacion=res.id_usuario
inner join apartan ap
on res.id=ap.idreserva
inner join
habitacion hab
on ap.numerohabitacion=hab.numerohabitacion
inner join sirven sirv
on hab.numerohabitacion=sirv.numerohabitacion
where sirv.FECHAUSO between 2019010101 and 2019070101
and sirv.IDSERVICIO=1
group by us.identificacion, us.nombre;


RFC10-Terminado

select *
from usuario
where identificacion not in(
Select us.identificacion
from usuario us
inner join reserva res
on us.identificacion=res.id_usuario
inner join apartan ap
on res.id=ap.idreserva
inner join
habitacion hab
on ap.numerohabitacion=hab.numerohabitacion
inner join sirven sirv
on hab.numerohabitacion=sirv.numerohabitacion
where sirv.FECHAUSO between 2019010101 and 2019070101
and sirv.IDSERVICIO=1);



RFC12-FUNCIONA
SELECT US.IDENTIFICACION
FROM USUARIO US
INNER JOIN RESERVA RES
ON US.IDENTIFICACION=RES.ID_USUARIO
INNER JOIN APARTAN AP
ON RES.ID=AP.IDRESERVA
INNER JOIN HABITACION HAB 
ON AP.NUMEROHABITACION=HAB.NUMEROHABITACION
INNER JOIN SIRVEN SIRV
ON HAB.NUMEROHABITACION=SIRV.NUMEROHABITACION
INNER JOIN SERVICIOS SERV
ON SIRV.IDSERVICIO=SERV.ID
WHERE SERV.COSTO>=20
OR SERV.NOMBRE LIKE'%SPA%'
OR SERV.NOMBRE LIKE '%SALA REUNIONES%'
OR SERV.NOMBRE LIKE '%SALA CONFERENCIAS%';




select usuario.identificacion, count(reserva.id)
from usuario
inner join reserva
on usuario.identificacion=reserva.id_usuario
where reserva.fechainicio between 20180101 and 20180401
or reserva.fechainicio between 20180401 and 20180701
or reserva.fechainicio between 20180701 and 20180901
or reserva.fechainicio between 20180901 and 20190101
group by usuario.identificacion
order by count(reserva.id) desc;





